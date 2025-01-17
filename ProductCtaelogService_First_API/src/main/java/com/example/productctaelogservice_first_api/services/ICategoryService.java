package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.models.Category;

import java.util.List;

public interface ICategoryService {
    Category findCategoryById(Long id);

    List<Category> getAllCategories();

    Category saveCategory(Category category);

    Category replaceCategory(Long id, Category category);
}
