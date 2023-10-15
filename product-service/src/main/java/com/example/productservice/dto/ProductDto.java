package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    @JsonIgnore
    private String name;
    private String description;
    private List<Integer> categoryIds;
    private float price;
    private int availableCount;
}
