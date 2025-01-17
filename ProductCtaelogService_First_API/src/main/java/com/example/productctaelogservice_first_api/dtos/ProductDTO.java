package com.example.productctaelogservice_first_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private CategoryDTO category;
//    private  Boolean isPrime;  //Check if the user is a prime member or not
}
