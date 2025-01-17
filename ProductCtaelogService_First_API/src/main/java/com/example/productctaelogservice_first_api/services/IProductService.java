package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.models.Product;

import java.util.List;

public interface IProductService {
    Product getProductById(Long productId);

    List<Product> getAllProducts();

//    Product createProduct(Product product);

    Product replaceProduct(Long ProductId, Product requestProduct);

    Product saveProduct(Product product);
}
