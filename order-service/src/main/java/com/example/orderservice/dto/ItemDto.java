package com.example.orderservice.dto;

import com.example.orderservice.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.context.properties.bind.Nested;

import java.io.Serializable;

@Data
public class ItemDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    int id;


    int count;

    int productId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Status status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    int orderId;
}
