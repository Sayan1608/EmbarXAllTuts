package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "/api/public/categories")
    public ResponseEntity<String> createNewCategory(@Valid @RequestBody Category category){
       categoryService.createNewCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully !!");
    }

    @DeleteMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
            String status = categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(status);
    }

    @PutMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category,
                                                 @PathVariable(name = "categoryId")Long categoryId){
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Category Updated Successfully !!",HttpStatus.OK);
    }
}
