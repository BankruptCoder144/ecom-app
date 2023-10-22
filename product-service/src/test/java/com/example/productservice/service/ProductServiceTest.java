package com.example.productservice.service;


import com.example.productservice.entity.ProductDetails;
import com.example.productservice.exceptions.AppException;
import com.example.productservice.repo.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void testOrderProduct_success() throws AppException {
        ProductDetails product = new ProductDetails();
        product.setId(1);
        product.setAvailableCount(10);
        product.setVersion(1);

        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));
        productService.orderProduct(1,6);
        Assert.assertEquals(4, product.getAvailableCount());
    }

    @Test(expected = AppException.class)
    public void testOrderProduct_productNotFound() throws AppException {
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.empty());

        try {
            productService.orderProduct(1, 13);
        } catch (AppException ex) {
            //Assert the error message
            Assert.assertEquals("Product with id:1 not found", ex.getMessage());
            throw ex;
        }
    }

    @Test(expected = AppException.class)
    public void testOrderProduct_failNotEnoughAvailableCount() throws AppException {
        ProductDetails product = new ProductDetails();
        product.setId(1);
        product.setAvailableCount(10);
        product.setVersion(1);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //Simulate concurrent update by changing version
        product.setVersion(2);

        try {
            productService.orderProduct(1, 13);
        } catch (AppException ex) {
            //Assert the error message
            Assert.assertEquals("Count exceeds available Count", ex.getMessage());
            throw ex;
        }
    }


}
