package com.store.bear.service;

import com.store.bear.exceptions.ResourceNotFoundException;
import com.store.bear.payload.CategoryDTO;
import com.store.bear.payload.CategoryResponse;
import jakarta.persistence.EntityExistsException;

public interface CategoryService {
    public CategoryResponse getAllCategories(int pageSize, int pageNumber, String sortBy, String sortOrder);
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws EntityExistsException;
    public CategoryDTO deleteCategory(Long categoryId) throws ResourceNotFoundException;
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws ResourceNotFoundException;
}
