package com.example.orderservice.util;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderUtil {

    public static Order orderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setItems(ItemUtil.listOfItemDtoToItems(orderDto.getItems()));
        order.setUserId(orderDto.getUserId());
        order.setAmount(orderDto.getTotalAmount());
        return order;
    }

    public static OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setItems(ItemUtil.listOfItemsToItemDtos(order.getItems()));
        orderDto.setUserId(order.getUserId());
        return orderDto;
    }

    public static List<OrderDto> orderListToOrderDtos(List<Order> orders) {
        return orders.stream().map(OrderUtil::orderToOrderDto).collect(Collectors.toList());
    }
}
