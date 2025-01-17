package com.example.productctaelogservice_first_api.repositories;

import com.example.productctaelogservice_first_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    Product save(Product product);

    List<Product> findAll();

//    Find Products Order By Price in ascending order
    List<Product> findProductByOrderByPrice();

//    Custom Query
    @Query("Select p.name from Product p where p.id=?1")
    Product findProductByTitleById(Long id);
}
