package com.store.bear.service;

import com.store.bear.model.Category;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllCategories();
    public void createCategory(Category category);
    public void deleteCategory(Long categoryId) throws ResponseStatusException;
    public Category updateCategory(Long categoryId, Category category) throws ResponseStatusException;
}
