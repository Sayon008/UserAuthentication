package com.example.productctaelogservice_first_api.services;

import com.example.productctaelogservice_first_api.models.Category;
import com.example.productctaelogservice_first_api.models.Product;
import com.example.productctaelogservice_first_api.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageCategoryService implements ICategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);

        if(categoryOptional.isEmpty()){
            return null;
        }

        return categoryOptional.get();
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category replaceCategory(Long id, Category newCategoryData) {
        Optional<Category> existingCategoryOptional = categoryRepo.findById(id);

        if(existingCategoryOptional.isEmpty()){
            return null;
        }

        Category existingCategory = existingCategoryOptional.get();

        existingCategory.setName(newCategoryData.getName());
        existingCategory.setDescription(newCategoryData.getDescription());
        if(existingCategory.getProducts() != null){
            existingCategory.setProducts(newCategoryData.getProducts());
        }

        return categoryRepo.save(existingCategory);
    }
}
