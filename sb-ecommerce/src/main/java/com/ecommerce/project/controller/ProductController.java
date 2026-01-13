package com.ecommerce.project.controller;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Add product to Category
    @PostMapping("/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProductToCategory(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId){
        return new ResponseEntity<>(productService.addProductToCategory(productDTO, categoryId),HttpStatus.CREATED);
    }


}
