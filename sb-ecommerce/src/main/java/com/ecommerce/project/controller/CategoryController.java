package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/api/public/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping(path = "/api/public/categories")
    public String createNewCategory(@RequestBody Category category){
       categoryService.createNewCategory(category);
        return "Category Added Successfully!";
    }

    @DeleteMapping(path = "/api/admin/category/{categoryId}")
    public String deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
