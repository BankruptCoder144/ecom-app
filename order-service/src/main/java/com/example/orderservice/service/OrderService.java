package com.example.orderservice.service;

import com.example.orderservice.dto.ItemDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.ProductDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.exceptions.AppException;
import com.example.orderservice.repo.OrderRepository;
import com.example.orderservice.util.OrderUtil;
import com.example.orderservice.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        orderRepository.save(OrderUtil.orderDtoToOrder(orderDto));
        return orderDto;
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
