package com.example.productctaelogservice_first_api.repositories;

import com.example.productctaelogservice_first_api.models.Category;
import com.example.productctaelogservice_first_api.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {
    @Autowired
    private CategoryRepo categoryRepo;

//    @Test
//    @Transactional
//    void testFetchType() {
//        Category category = categoryRepo.findById(21L).get();
//        for(Product product: category.getProducts()){
//            System.out.println(product.getName());
//        }
//    }

    @Test
    @Transactional
    void testSomething(){
        List<Category> listCategory = categoryRepo.findAll();
        for(Category category: listCategory){
            for(Product product: category.getProducts()){
                System.out.println(product.getName());
            }
        }

    }
}