package com.example.productctaelogservice_first_api.repositories;

import com.example.productctaelogservice_first_api.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void testJPA(){
        List<Product> productListByPrice = productRepo.findProductByOrderByPrice();
        for(Product product:productListByPrice){
            System.out.println(product.getPrice());
        }

    }
}