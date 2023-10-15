package com.example.productservice.controller;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.entity.CategoryDetails;
import com.example.productservice.entity.ProductDetails;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @PostMapping("/category")
    public CategoryDetails addNewCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }

    @GetMapping("/category")
    public List<CategoryDetails> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/")
    public ProductDetails addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @GetMapping("/")
    public List<ProductDetails> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<ProductDetails> getProductDetails(@RequestParam("id") int id) {
        return productService.getProductdetails(id);
    }
}
