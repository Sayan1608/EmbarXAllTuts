package com.ecommerce.project.service;

import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProductToCategory(ProductDTO productDTO, Long categoryId);

    ProductResponse getAllProducts();
}
