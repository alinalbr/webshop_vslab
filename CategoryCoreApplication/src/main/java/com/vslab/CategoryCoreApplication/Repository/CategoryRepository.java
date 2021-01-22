package com.vslab.CategoryCoreApplication.Repository;

import com.vslab.CategoryCoreApplication.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id); 	// GET /Category/{categoryId}

    Optional<Category> findByName(String name);

    List<Category> findAll(); 				// GET /category

    void deleteById(Long id); 				// DELETE /Category/{categoryId}

    Category save(Category category);		// POST /Category
}
