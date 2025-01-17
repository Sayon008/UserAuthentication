package com.example.productctaelogservice_first_api.controllers;

import com.example.productctaelogservice_first_api.dtos.ProductDTO;
import com.example.productctaelogservice_first_api.models.Product;
import com.example.productctaelogservice_first_api.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerMvcTest {
    @MockBean
    private IProductService productService;

    //MockMvc is a Simulation of a PostMan -> as If a real person is testing the scenario using a postMan service
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProducts_ResultInReturnOfAllProducts() throws Exception {
//    Arrange
//        Mocking the body for the Product -> Creating a dummy product json
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Iphone12");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Samsung Galaxy");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productService.getAllProducts()).thenReturn(productList);

//        Now from the Json response we will be getting will be of ProductDTO object
//        Creating the mock for the ProductDto objects ->

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        productDTO1.setName("Iphone12");

        ProductDTO productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setName("Samsung Galaxy");

        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO1);
        productDTOList.add(productDTO2);


//        perform() -> request
//        andExpect() -> response
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDTOList)));
    }

    @Test
    public void Test_CreateProducts_RunSuccessfully() throws Exception {
        Product product = new Product();
        product.setId(7L);
        product.setName("Samsung Galaxy S23");

        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(7L);
        productDTO.setName("Samsung Galaxy S23");

        mockMvc.perform(post("/products")
                .content(objectMapper.writeValueAsString(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(productDTO)))
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

}
