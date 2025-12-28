package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryList
                .stream().filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with categoryId: "+ categoryId +" not found !!"));

        categoryList.remove(category);
        return "Category with categoryId: " + categoryId +" deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Optional<Category> optionalCategory = categoryList
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Category with categoryId: "+ categoryId +" not found !!");
        }
    }
}
