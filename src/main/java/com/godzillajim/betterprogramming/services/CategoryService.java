package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.mappers.CategoryBody;
import com.godzillajim.betterprogramming.domain.mappers.CategoryMapper;
import com.godzillajim.betterprogramming.domain.payloads.CategoryRequest;
import com.godzillajim.betterprogramming.domain.repositories.ICategoryRepository;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    final ICategoryRepository categoryRepository;
    public CategoryBody getCategoryById(Long categoryId){
        Category category = categoryRepository
                .findCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id: %s does not exist", categoryId)));
        return CategoryMapper.mapCategoryToCategoryBody(category);
    }
    public Category getCategoryByName(String name){
        return categoryRepository
                .findCategoryByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with name: %s does not exist", name)));
    }
    public CategoryBody createCategory(CategoryRequest request) {
        Category category = CategoryMapper.mapCategoryRequestToCategory(request);
        try {
            return CategoryMapper.mapCategoryToCategoryBody(categoryRepository.save(category));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<CategoryBody> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryBody> categoryBodies = new ArrayList<>();
        categories.forEach(category -> categoryBodies.add(CategoryMapper.mapCategoryToCategoryBody(category)));
        return categoryBodies;
    }
    public CategoryBody updateCategory(CategoryBody categoryBody, Long id) {
        Category category = categoryRepository
                .findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %s does not exist", id)));
        category.setName(categoryBody.getName());
        return CategoryMapper.mapCategoryToCategoryBody(categoryRepository.save(category));
    }
    public Boolean deleteCategory(Long categoryId) {
        Category category = categoryRepository
                .findCategoryById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %s does not exist", categoryId)));
        try {
            categoryRepository.delete(category);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public List<CategoryBody> searchCategories(String query) {
        List<Category> categories = categoryRepository.findByNameContainsAndArchived(query,false);
        List<CategoryBody> categoryBodies = new ArrayList<>();
        categories.forEach(category -> categoryBodies.add(CategoryMapper.mapCategoryToCategoryBody(category)));
        return categoryBodies;
    }
    public CategoryBody archiveCategory(Long id) {
        Category category = categoryRepository
                .findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %s does not exist", id)));
        category.setArchived(true);
        try {
            return CategoryMapper.mapCategoryToCategoryBody(categoryRepository.save(category));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public CategoryBody unArchiveCategory(Long id) {
        Category category = categoryRepository
                .findCategoryById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with id %s does not exist", id)));
        category.setArchived(false);
        try {
            return CategoryMapper.mapCategoryToCategoryBody(categoryRepository.save(category));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
