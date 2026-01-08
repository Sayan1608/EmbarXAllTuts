package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryServiceImpl implements CategoryService{
    AtomicLong id = new AtomicLong(0L);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) throw new APIException("No Categories Present!");
        return categories;
    }

    @Override
    public void createNewCategory(Category category) {
//        incrementCategoryId(category); // fixed hibernate state object exception
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null) throw new APIException("Category with categoryName : " + category.getCategoryName() + " already exists!");
        categoryRepository.save(category);
    }

    @Override
    public void incrementCategoryId(Category category) {
       category.setCategoryId(id.incrementAndGet());
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));

        categoryRepository.deleteById(categoryId);
        return "Category with categoryId: " + categoryId +" deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        Category savedDBCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedDBCategory != null) throw new APIException("Category with categoryName : " + category.getCategoryName() + " already exists!");
        Category savedCategory;

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}
