package com.store.bear.service;

import com.store.bear.exceptions.ResourceNotFoundException;
import com.store.bear.model.Category;
import com.store.bear.payload.CategoryDTO;
import com.store.bear.payload.CategoryResponse;
import com.store.bear.repository.CategoryRepository;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse getAllCategories(int pageSize, int pageNumber, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoriesPage = categoryRepository.findAll(pageDetails);
        List<CategoryDTO> categoryDTOS = categoriesPage.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        return new CategoryResponse(categoryDTOS,categoriesPage.getNumber(), categoriesPage.getSize(), categoriesPage.getTotalElements(), categoriesPage.getTotalPages(), categoriesPage.isLast());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws EntityExistsException {
        Category savedCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if(savedCategory != null) {
            throw new EntityExistsException();
        }
        return modelMapper.map(categoryRepository.save(modelMapper.map(categoryDTO, Category.class)), CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isEmpty()) {
            throw new ResourceNotFoundException("category", "categoryId", categoryId);
        }
        categoryRepository.deleteById(categoryId);
        return modelMapper.map(category.get(), CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) throws ResourceNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException("category", "categoryId", categoryId);
        }

        optionalCategory.get().setCategoryName(categoryDTO.getCategoryName());
        categoryRepository.save(optionalCategory.get());
        return modelMapper.map(optionalCategory.get(), CategoryDTO.class);
    }
}
