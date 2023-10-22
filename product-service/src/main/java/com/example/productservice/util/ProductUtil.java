package com.example.productservice.util;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.entity.ProductDetails;

import java.util.List;
import java.util.stream.Collectors;

public class ProductUtil {

    public static ProductDto productDetailsToProductDto(ProductDetails productDetails) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productDetails.getId());
        productDto.setName(productDetails.getName());
        productDto.setPrice(productDetails.getPrice());
        productDto.setAvailableCount(productDetails.getAvailableCount());
        productDto.setCategoryIds(
                productDetails.getProductCategories().stream()
                        .map(categoryDetails -> categoryDetails.getId()).collect(Collectors.toList()));
        return productDto;
    }

    public static List<ProductDto> productDetailsListToDtos(List<ProductDetails> productDetails) {
        return productDetails.stream().map(ProductUtil::productDetailsToProductDto).collect(Collectors.toList());
    }

}
