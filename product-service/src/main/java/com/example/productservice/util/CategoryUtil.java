package com.example.productservice.util;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.entity.CategoryDetails;

public class CategoryUtil {

    public static CategoryDto categoryDetailsToCategoryDto(CategoryDetails categoryDetails) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription(categoryDto.getDescription());
    }
}
