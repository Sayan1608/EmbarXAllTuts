package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(path = "/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){
        return ResponseEntity.ok(categoryService.getAllCategories(pageNumber,pageSize));
    }

    @PostMapping(path = "/api/public/categories")
    public ResponseEntity<CategoryDTO> createNewCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO newCategory = categoryService.createNewCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
    }

    @DeleteMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable(name = "categoryId") Long categoryId){
            CategoryDTO categoryDTO =  categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(categoryDTO);
    }

    @PutMapping(path = "/api/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable(name = "categoryId")Long categoryId){
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);
            return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
}
