package com.example.productctaelogservice_first_api.controllers;

import com.example.productctaelogservice_first_api.dtos.ProductDTO;
import com.example.productctaelogservice_first_api.models.Product;
import com.example.productctaelogservice_first_api.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
//ProductController is a Spring Component and we can autowire this in this tets class
class ProductControllerTest {

    @Autowired
    private ProductController productController;

//    We have a dependency of the IProductService interface in the ProductController class
//    Since this is the test class we will only be mocking the IproductService Interface only -> make it as a simulation that's it
//    Any class/anything which has external a external dependency to ProductController should be mocked

//    It will create a mock->Simulation of the IProductService Bean
    @MockBean
    private IProductService productService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
//    To give more context to developer about what is the test function is actually doing
    @DisplayName("get product with id 4 will actually run fine or not")
    public void Test_GetPRoductById_ReturnsProductSuccessfully() {
//        Arrange
        Long productId = 4L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");

//        When and Then Syntax
        when(productService.getProductById(productId)).thenReturn(product);

//        Act
        ResponseEntity<ProductDTO> response = productController.findProductById(productId);

//        Assert
        assertNotNull(response);
//        A strict check than the assertNotNull(response)
        assertNotNull(response.getBody());
//        Also assert/check if the productId is 4 or not
        assertEquals(productId, response.getBody().getId());
        assertEquals("Iphone", response.getBody().getName());
        verify(productService,times(1)).getProductById(productId);
    }


    @Test
//    Test the getProductById() with Exception -> If the exceptions are working or not correctly
    public  void Test_GetProductById_CalledWithInvalidId_ResultsInIllegalArgumentException(){

//        Act and Assert
       Exception exception = assertThrows(IllegalArgumentException.class, () -> productController.findProductById(-1L));

        assertEquals("Please try with productId > 0",exception.getMessage());
        verify(productService,never()).getProductById(anyLong());
    }

    @Test
    public  void Test_GetProductById_CalledWithProductIdEqualsZero_ResultsInIllegalArgumentException(){
        //        Act and Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productController.findProductById(0L));

        assertEquals("Product Id 0 is not present",exception.getMessage());
    }

    @Test
//    Captor Argument
    public void Test_GetProductById_ProductServiceCalledWithCorrectArgument_RunSuccessfully(){
//        Arrange
        Long productId = 4L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");

        when(productService.getProductById(productId)).thenReturn(product);

//        Act
        ResponseEntity<ProductDTO> response = productController.findProductById(productId);

//        Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());

    }
}