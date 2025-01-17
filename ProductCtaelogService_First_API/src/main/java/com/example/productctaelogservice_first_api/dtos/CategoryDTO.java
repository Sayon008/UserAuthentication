package com.example.productctaelogservice_first_api.dtos;

import com.example.productctaelogservice_first_api.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private List<ProductDTO> products;
}
