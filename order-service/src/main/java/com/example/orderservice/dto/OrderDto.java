package com.example.orderservice.dto;


import com.example.orderservice.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto {

    int id;

    @JsonIgnore
    int userId;

    @JsonProperty("items")
    List<ItemDto> items;

    float totalAmount;
}
