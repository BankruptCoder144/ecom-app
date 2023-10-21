package com.example.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    @JsonIgnore
    private int id;
    private String name;
    private List<Integer> categoryIds;
    private float price;
    private int availableCount;
}
