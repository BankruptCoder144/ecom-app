package com.example.productservice.controller;

import com.example.productservice.dto.CategoryDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.exceptions.AppException;
import com.example.productservice.service.CategoryService;
import com.example.productservice.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddNewCategory_success() throws Exception {
        String body = "{\"name\" : \"testCategory\", \"description\" : \"Category for Unit Test\" }";
        mockMvc.perform(post("/product/category")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());

        Mockito.verify(categoryService, Mockito.times(1)).addCategory(any(CategoryDto.class));
    }

    @Test
    public void testGetAllCategory_success() throws Exception {
        List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>() {{
           add(new CategoryDto(1, "test1", "test category 1"));
           add(new CategoryDto(2, "test2", "test category 2"));
           add(new CategoryDto(3, "test3", "test category 3"));

        }};

        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryDtoList);
        mockMvc.perform(get("/product/category"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"name\":\"test1\",\"description\":\"test categor" +
                        "y 1\"},{\"id\":2,\"name\":\"test2\",\"description\":\"test category 2\"},{\"id\":3,\"name\":" +
                        "\"test3\",\"description\":\"test category 3\"}]"));
    }

    @Test
    public void testAddProduct_success() throws Exception {
        String body = "{\n" +
                "    \"name\" : \"iPhone\",\n" +
                "    \"description\" : \"iPhone Smartphone\",\n" +
                "    \"price\" : 599.35,\n" +
                "    \"availableCount\" : 4,\n" +
                "    \"categoryIds\" : [2,4]\n" +
                "}";
        mockMvc.perform(post("/product/")
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk());

        Mockito.verify(productService, Mockito.times(1)).addProduct(any(ProductDto.class));
    }

    @Test
    public void testOrderProduct_success() throws Exception {
        mockMvc.perform(put("/product/order?pid=1&count=2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order successfully created for pid : 1"));

        Mockito.verify(productService, Mockito.times(1)).orderProduct(1, 2);
    }

    @Test
    public void testOrderProduct_ConcurrentUpdate() throws Exception {
        Mockito.doThrow(new AppException("Concurrent update detected. Please try again later",
                HttpStatus.BAD_REQUEST)).when(productService).orderProduct(1, 2);

        mockMvc.perform(put("/product/order?pid=1&count=2"))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .string("{\"message\":\"Concurrent update detected. Please try again later\"}"));

        Mockito.verify(productService, Mockito.times(1)).orderProduct(1, 2);
    }
}
