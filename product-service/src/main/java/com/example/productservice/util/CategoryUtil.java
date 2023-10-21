package com.example.productservice.util;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.entity.CategoryDetails;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryUtil {

    public static CategoryDto categoryDetailsToCategoryDto(@NonNull CategoryDetails categoryDetails) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription(categoryDto.getDescription());
        categoryDto.setName(categoryDetails.getName());
        return categoryDto;
    }

    public static List<CategoryDto> categoryDetailsListToDtoList(@NonNull List<CategoryDetails> categoryDetailsList) {
        return categoryDetailsList.stream().map(CategoryUtil::categoryDetailsToCategoryDto).collect(Collectors.toList());
    }

    public static CategoryDetails categoryDtoToCategoryDetails(@NonNull CategoryDto categoryDto) {
        CategoryDetails categoryDetails = new CategoryDetails();
        categoryDetails.setDescription(categoryDto.getDescription());
        categoryDetails.setName(categoryDto.getName());
        return categoryDetails;
    }
}
