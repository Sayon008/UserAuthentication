package com.example.productctaelogservice_first_api.controllers;

import com.example.productctaelogservice_first_api.dtos.CategoryDTO;
import com.example.productctaelogservice_first_api.dtos.ProductDTO;
import com.example.productctaelogservice_first_api.models.Category;
import com.example.productctaelogservice_first_api.models.Product;
import com.example.productctaelogservice_first_api.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategory(){
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        List<Category> categories = categoryService.getAllCategories();

        for (Category category : categories) {
            categoryDTOList.add(from(category));
        }

        return categoryDTOList;

    }

    @GetMapping("{category_id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("category_id") Long id){
        Category category = categoryService.findCategoryById(id);

        if(category == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(from(category), HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO){

        Category category = from(categoryDTO);

        Category savedCategory = categoryService.saveCategory(category);

        if(savedCategory == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        CategoryDTO newCategoryDTO = from(savedCategory);

        return new ResponseEntity<>(newCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public CategoryDTO replaceCategory(Long id,CategoryDTO categoryDTO){
        Category category = from(categoryDTO);

        Category savedCategory = categoryService.replaceCategory(id, category);

        if(savedCategory == null) {
            return null;
        }

        return from(savedCategory);
    }

    private CategoryDTO from(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());

        List<ProductDTO> productDTOList = new ArrayList<>();
        if(category.getProducts() != null){
            for (Product product : category.getProducts()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setDescription(product.getDescription());
                productDTO.setPrice(product.getPrice());
                productDTO.setImageUrl(product.getImageUrl());
                productDTOList.add(productDTO);
            }

            categoryDTO.setProducts(productDTOList);
        }

        return categoryDTO;
    }


    private Category from(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        if(category.getProducts() != null){
            List<Product> productList = new ArrayList<>();
            for (ProductDTO productDTO : categoryDTO.getProducts()) {
                Product product = new Product();
                product.setId(productDTO.getId());
                product.setName(productDTO.getName());
                product.setDescription(productDTO.getDescription());
                product.setPrice(productDTO.getPrice());
                product.setImageUrl(productDTO.getImageUrl());

                product.setCategory(category);

                productList.add(product);
            }
            category.setProducts(productList);
        }
        return category;
    }
}
