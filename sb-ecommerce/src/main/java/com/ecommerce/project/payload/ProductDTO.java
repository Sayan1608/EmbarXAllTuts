package com.ecommerce.project.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    @NotBlank(message = "Product name is required")
    @Size(min = 3 , message = "productName must contain at least 3 characters")
    private String productName;
    private String description;
    private String image;
    private Integer quantity;
    private double price;
    private double discount;
    private Double specialPrice;
}
