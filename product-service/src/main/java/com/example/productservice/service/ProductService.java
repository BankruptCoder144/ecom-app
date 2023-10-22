package com.example.productservice.service;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.entity.CategoryDetails;
import com.example.productservice.entity.ProductDetails;
import com.example.productservice.exceptions.AppException;
import com.example.productservice.repo.CategoryRepository;
import com.example.productservice.repo.ProductRepository;
import com.example.productservice.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public ProductDto addProduct(ProductDto productDto) {
        ProductDetails productDetails = createProductFromProductDto(productDto);
        productRepository.save(productDetails);
        productDto.setId(productDetails.getId());
        return productDto;
    }

    @Transactional
    public void orderProduct(int productId, int count) throws AppException {
        ProductDetails productDetails = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(
                        String.format("Product with id:%s not found", productId), HttpStatus.BAD_REQUEST));

        if(count > productDetails.getAvailableCount()) {
            throw new AppException("Count exceeds available Count", HttpStatus.BAD_REQUEST);
        }
        productDetails.setAvailableCount(productDetails.getAvailableCount() - count);

        try {
            productRepository.save(productDetails);
        } catch (OptimisticLockingFailureException ex) {
            throw new AppException("Concurrent update detected. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void updateProduct(int productId, ProductDto productDto) throws AppException {
        ProductDetails productDetails = createProductFromProductDto(productDto);
        productDetails.setId(productId);
        productRepository.save(productDetails);
    }

    public List<ProductDto> getAllProducts() {
        return ProductUtil.productDetailsListToDtos(productRepository.findAll());
    }

    public ProductDto getProductdetails(int pid) {
         Optional<ProductDetails> productDetails = productRepository.findById(pid);
         return productDetails.map(ProductUtil::productDetailsToProductDto)
                 .orElseThrow(() -> new RuntimeException("Product Id not found"));
    }

    private ProductDetails createProductFromProductDto(ProductDto productDto) {
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
        return productDetails;
    }

}
