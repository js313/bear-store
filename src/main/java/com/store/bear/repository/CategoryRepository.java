package com.store.bear.repository;

import com.store.bear.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> { // OR "CrudRepository" has fewer methods
    Category findByCategoryName(String categoryName);
}

