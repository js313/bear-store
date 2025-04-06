package com.store.bear.controller;

import com.store.bear.Config.AppConstants;
import com.store.bear.payload.CategoryDTO;
import com.store.bear.payload.CategoryResponse;
import com.store.bear.service.CategoryService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/category")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(value = "pageSize", defaultValue = ""+AppConstants.defaultPageSize) int pageSize,
            @RequestParam(value = "pageNumber", defaultValue = ""+AppConstants.defaultPageNumber) int pageNumber,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.defaultSortBy) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.defaultSortOrder) String sortOrder) {
        return ResponseEntity.ok().body(categoryService.getAllCategories(pageSize, pageNumber, sortBy, sortOrder));
    }

    @PostMapping("/public/category")
    public ResponseEntity<CategoryDTO> setCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategoryDTO);
        } catch (EntityExistsException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/public/category/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.deleteCategory(categoryId));
        } catch(ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(null);
        }
    }

    @PutMapping("/public/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } catch(ResponseStatusException error) {
            return ResponseEntity.status(error.getStatusCode()).body(null);
        }
    }
}
