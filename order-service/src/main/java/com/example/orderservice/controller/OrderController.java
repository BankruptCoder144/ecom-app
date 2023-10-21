package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.exceptions.AppException;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.util.TokenUtil;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/")
    public OrderDto createOrder(@RequestBody OrderDto orderDto,
                                @RequestHeader(value="authorization",
                                        required = false) String authHeader) throws AppException {
        return orderService.createOrder(orderDto, TokenUtil.extractAuthToken(authHeader));
    }

    @GetMapping("/")
    public List<OrderDto> getAllOrders(@RequestHeader(value="authorization",
                                            required = false) String authHeader) throws AppException {
        return orderService.getAllOrders(TokenUtil.extractAuthToken(authHeader));
    }

    @GetMapping("/{oid}")
    public OrderDto getOrderDetails(@PathVariable int oid,
                                    @RequestHeader(value="authorization",
                                            required = false) String authHeader) throws AppException {
        return orderService.getOrderDetails(oid, TokenUtil.extractAuthToken(authHeader));
    }
}
