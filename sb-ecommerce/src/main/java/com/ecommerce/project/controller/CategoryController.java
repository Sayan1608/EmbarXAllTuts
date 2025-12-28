package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryServiceImpl;
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
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping(path = "/api/public/categories")
    public ResponseEntity<String> createNewCategory(@RequestBody Category category){
       categoryService.createNewCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully !!");
    }

    @DeleteMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
        try {
            String status = categoryService.deleteCategory(categoryId);
//            return new ResponseEntity<>(status, HttpStatus.OK);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
            return ResponseEntity.ok(status);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable(name = "categoryId")Long categoryId){
        try {
            Category updatedCategory = categoryService.updateCategory(category, categoryId);
            return new ResponseEntity<>("Category Updated Successfully !!",HttpStatus.OK);
        } catch (ResponseStatusException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }
    }
}
