package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryServiceImpl implements CategoryService{
    private List<Category> categoryList = new ArrayList<>();
    AtomicLong id = new AtomicLong(0L);

    @Override
    public List<Category> getAllCategories() {
        return  categoryList;
    }

    @Override
    public void createNewCategory(Category category) {
        incrementCategoryId(category);
        categoryList.add(category);
    }

    @Override
    public void incrementCategoryId(Category category) {
       category.setCategoryId(id.incrementAndGet());
    }
}
