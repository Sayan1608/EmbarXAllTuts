package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryServiceImpl implements CategoryService{
    AtomicLong id = new AtomicLong(0L);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return  categoryRepository.findAll();
    }

    @Override
    public void createNewCategory(Category category) {
//        incrementCategoryId(category); // fixed hibernate state object exception
        categoryRepository.save(category);
    }

    @Override
    public void incrementCategoryId(Category category) {
       category.setCategoryId(id.incrementAndGet());
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categoryList = categoryRepository.findAll();
        Category category = categoryList
                .stream().filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Category with categoryId: "+ categoryId +" not found !!"));

        categoryRepository.deleteById(categoryId);
        return "Category with categoryId: " + categoryId +" deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        List<Category> categoryList = categoryRepository.findAll();
        Optional<Category> optionalCategory = categoryList
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existingCategory);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Category with categoryId: "+ categoryId +" not found !!");
        }
    }
}
