package com.ecommerce.project.payload;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    @Size(min = 3 , message = "categoryName must contain atleast 3 characters")
    private String categoryName;
}
