package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.dtos.FakeStoreProductDTO;
import com.example.productctaelogservice_first_api.models.Category;
import com.example.productctaelogservice_first_api.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeProductService implements IProductService{

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public Product getProductById(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDTO> fakeStoreProductDTOResponseEntity =
                restTemplate.getForEntity("https://fakestoreapi.com/products/{productId}", FakeStoreProductDTO.class,productId);

        if(fakeStoreProductDTOResponseEntity.getStatusCode().equals(HttpStatusCode.valueOf(200)) && fakeStoreProductDTOResponseEntity.getBody() != null){
            return from(fakeStoreProductDTOResponseEntity.getBody());
        }
        return null;
    }

    private Product from(FakeStoreProductDTO fakeStoreProductDTO) {
        Product product = new Product();
        product.setId(fakeStoreProductDTO.getId());
        product.setName(fakeStoreProductDTO.getTitle());
        product.setDescription(fakeStoreProductDTO.getDescription());
        product.setPrice(fakeStoreProductDTO.getPrice());

        Category category = new Category();
        category.setName(fakeStoreProductDTO.getCategory());

        product.setCategory(category);

        return product;
    }
}
