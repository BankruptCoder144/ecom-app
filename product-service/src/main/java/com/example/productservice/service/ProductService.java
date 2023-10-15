package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.entity.CategoryDetails;
import com.example.productservice.entity.ProductDetails;
import com.example.productservice.repo.CategoryRepository;
import com.example.productservice.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductDetails addProduct(ProductDto productDto) {
        List<CategoryDetails> categoryDetailsList = new ArrayList<>();
        productDto.getCategoryIds().forEach(categoryId ->{
            Optional<CategoryDetails> categoryDetails = categoryRepository.findById(categoryId);
            categoryDetailsList.add(
                    categoryDetails.stream().findFirst().orElseThrow(() -> new RuntimeException("Invalid CategoryId"))
            );
        });
        ProductDetails productDetails = new ProductDetails();
        productDetails.setName(productDto.getName());
        productDetails.setPrice(productDto.getPrice());
        productDetails.setAvailableCount(productDto.getAvailableCount());
        productDetails.setProductCategories(categoryDetailsList);
        productRepository.save(productDetails);
        return productDetails;
    }

    public List<ProductDetails> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductDetails> getProductdetails(int pid) {
        return productRepository.findById(pid);
    }

}
