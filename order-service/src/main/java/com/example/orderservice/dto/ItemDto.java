package com.example.orderservice.dto;

import com.example.orderservice.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.boot.context.properties.bind.Nested;

import java.io.Serializable;

@Data
public class ItemDto {

    @JsonIgnore
    int id;

    int count;

    int productId;

    @JsonIgnore
    ProductDto productDto;

    @JsonIgnore
    Status status;

    @JsonIgnore
    int orderId;
}
