package com.ecommerce.project.controller;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
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
    @PostMapping("admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProductToCategory(@RequestBody ProductDTO productDTO, @PathVariable Long categoryId){
        return new ResponseEntity<>(productService.addProductToCategory(productDTO, categoryId),HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        ProductResponse productResponse = productService.searchProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO>updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long productId){
        return new ResponseEntity<>(productService.updateProduct(productDTO, productId),HttpStatus.OK);
    }


}
