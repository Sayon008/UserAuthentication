//package com.example.productctaelogservice_first_api.services;
//
//import com.example.productctaelogservice_first_api.models.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class FakeStoreCategoryService {
//
//    @Autowired
//    private RestTemplateBuilder restTemplateBuilder;
//
//    public Category getAllCategory() {
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<FakeStoreCategoryDTO> categoryListResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/categories",FakeStoreCategoryDTO.class);
//
//        if(categoryListResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && categoryListResponseEntity.getBody() != null) {
//            return
//        }
//    }
//}
