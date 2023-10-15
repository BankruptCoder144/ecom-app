package com.example.productservice.service;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.entity.CategoryDetails;
import com.example.productservice.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDetails addCategory(CategoryDto categoryDto) {
        CategoryDetails categoryDetails = new CategoryDetails();
        categoryDetails.setName(categoryDto.getName());
        categoryDetails.setDescription(categoryDto.getDescription());
        categoryRepository.save(categoryDetails);
        return categoryDetails;
    }

    public List<CategoryDetails> getAllCategories() {
        return categoryRepository.findAll();
    }
}
