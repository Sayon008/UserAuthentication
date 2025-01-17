package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.models.Product;
import com.example.productctaelogservice_first_api.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("sps")
@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepo.findById(productId);

        if(productOptional.isEmpty()) return null;

        return productOptional.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

//    @Override
//    public Product createProduct(Product product) {
//        return null;
//    }

    @Override
    public Product replaceProduct(Long ProductId, Product requestProduct) {
        // Step 1: Check if the product exists
        Optional<Product> existingProductOptional = productRepo.findById(ProductId);

        // Product not found, handle as needed (return null, throw exception, etc.)
        if(existingProductOptional.isEmpty()) return null;

        // Step 2: Get the existing product
        Product existingProduct = existingProductOptional.get();

        // Step 3: Update the fields of the existing product with the request product's details

        existingProduct.setName(requestProduct.getName());
        existingProduct.setDescription(requestProduct.getDescription());
        existingProduct.setPrice(requestProduct.getPrice());
        existingProduct.setCategory(requestProduct.getCategory());
        existingProduct.setImageUrl(requestProduct.getImageUrl());

        // Step 4: Save the updated product
        Product updatedProduct = productRepo.save(existingProduct);

        // Step 5: Return the updated product
        return updatedProduct;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }
}
