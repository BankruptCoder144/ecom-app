package com.example.productservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CategoryDto {

    @JsonIgnore
    private int id;
    private String name;
    private String description;
}
