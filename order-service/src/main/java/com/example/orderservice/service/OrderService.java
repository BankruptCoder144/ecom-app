package com.example.orderservice.service;

import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.entity.Item;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.Status;
import com.example.orderservice.exceptions.AppException;
import com.example.orderservice.repo.OrderRepository;
import com.example.orderservice.util.OrderUtil;
import com.example.orderservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestTemplate restTemplate;

    public OrderDto createOrder(OrderDto orderDto, String token) {
        calculateTotal(orderDto);
        orderDto.setUserId(TokenUtil.getUserIdFromToken(token));
        Order order = OrderUtil.orderDtoToOrder(orderDto);
        System.out.println("qwknjbqkdq");
        processOrder(order);
        order = orderRepository.save(order);
        orderDto.setId(order.getId());
        return orderDto;
    }

    private void processOrder(Order order) {
        System.out.println("swfwew");
        for(Item item : order.getItems()) {
            System.out.println("adasdas");
            try {
                ResponseEntity<String> productOrderResponse = restTemplate.exchange(String.format("http://PRODUCT-SERVICE/product/order?pid=%s&count=%s",
                        item.getProductId(), item.getCount()), HttpMethod.PUT, null, String.class);
                if (!productOrderResponse.getStatusCode().equals(HttpStatus.OK)) {
                    item.setStatus(Status.FAILED);
                } else {
                    item.setStatus(Status.SUCCESS);
                }
            } catch (Exception ex) {
                item.setStatus(Status.FAILED);
            }
        }
    }

    public List<OrderDto> getAllOrders(String token) {
        System.out.println(TokenUtil.getUserIdFromToken(token));
        return OrderUtil.orderListToOrderDtos(orderRepository.getAllOrderByUserId(TokenUtil.getUserIdFromToken(token)));
    }

    public OrderDto getOrderDetails(int oid, String token) throws AppException {
        Optional<Order> orderOptional = orderRepository.findById(oid);
        if(orderOptional.isEmpty()) {
            throw new AppException("Order not found", HttpStatus.BAD_REQUEST);
        }
        Order order = orderOptional.get();
        if (order.getUserId()!= TokenUtil.getUserIdFromToken(token)) {
            throw new AppException("Don't have permission to access this order", HttpStatus.FORBIDDEN);
        }
        return OrderUtil.orderToOrderDto(order);
    }

    private void calculateTotal(OrderDto orderDto) {
        float amount = 0f;

        for (ItemDto item : orderDto.getItems()) {
            ProductDto productDto = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + item.getProductId(), ProductDto.class);
            if (productDto == null) {
                throw new RuntimeException("Failed to fetch product details for productId : " + item.getProductId());
            }

            amount += item.getCount() * productDto.getPrice();
        }
        orderDto.setTotalAmount(amount);
    }

}
