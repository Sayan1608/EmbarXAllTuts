package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CategoryServiceImpl implements CategoryService{
    AtomicLong id = new AtomicLong(0L);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty()) throw new APIException("No Categories Present!");
        List<CategoryDTO> categoryDTOList = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse content = new CategoryResponse();
        content.setContent(categoryDTOList);
        content.setPageNumber(categoryPage.getNumber());
        content.setPageSize(categoryPage.getSize());
        content.setTotalElements(categoryPage.getTotalElements());
        content.setTotalPages(categoryPage.getTotalPages());
        content.setLastPage(categoryPage.isLast());
        return content;
    }

    @Override
    public CategoryDTO createNewCategory(CategoryDTO categoryDTO) {
//        incrementCategoryId(category); // fixed hibernate state object exception
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        existCategoryByName(savedCategory, category);
        Category persistedCategory = categoryRepository.save(category);
        return modelMapper.map(persistedCategory, CategoryDTO.class);
    }

    private static void existCategoryByName(Category savedCategory, Category category) {
        if(savedCategory != null) throw new APIException("Category with categoryName : " + category.getCategoryName() + " already exists!");
    }

    @Override
    public void incrementCategoryId(Category category) {
       category.setCategoryId(id.incrementAndGet());
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category savedCategory = findCategoryById(categoryId);

        categoryRepository.deleteById(categoryId);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }

    private Category findCategoryById(Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        Category savedCategory = savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("category","categoryId", categoryId));
        return savedCategory;
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);
        savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedDBCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        existCategoryByName(savedDBCategory, category);
        Category savedCategory;

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);
    }
}
