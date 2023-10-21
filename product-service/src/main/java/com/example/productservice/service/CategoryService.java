package com.example.productservice.service;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.entity.CategoryDetails;
import com.example.productservice.repo.CategoryRepository;
import com.example.productservice.util.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryDto categoryDto) {
        CategoryDetails categoryDetails = CategoryUtil.categoryDtoToCategoryDetails(categoryDto);
        categoryRepository.save(categoryDetails);
        categoryDto.setId(categoryDetails.getId());
        return categoryDto;
    }

    public List<CategoryDto> getAllCategories() {
        return CategoryUtil.categoryDetailsListToDtoList(categoryRepository.findAll());
    }
}
