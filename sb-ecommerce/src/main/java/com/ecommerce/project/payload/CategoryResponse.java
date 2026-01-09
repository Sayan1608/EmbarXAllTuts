package com.ecommerce.project.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CategoryResponse {
    private List<CategoryDTO> content;
}
