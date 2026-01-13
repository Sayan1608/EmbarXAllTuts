package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProductToCategory(ProductDTO productDTO, Long categoryId);
}
