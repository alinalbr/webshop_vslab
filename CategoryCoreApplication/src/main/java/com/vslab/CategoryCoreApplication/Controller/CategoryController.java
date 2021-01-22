package com.vslab.CategoryCoreApplication.Controller;

import com.vslab.CategoryCoreApplication.Model.Category;
import com.vslab.CategoryCoreApplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryRepository repo;


    @GetMapping("")
    public ResponseEntity<Category[]> getCategories() {
        List<Category> category = repo.findAll();
        Category[] categoryArray = category.stream().toArray(Category[] ::new);
        return new ResponseEntity<Category[]>(categoryArray, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable Long categoryId) {
        Optional<Category> category = repo.findById(categoryId);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Optional<Category>>(category, HttpStatus.OK);
        }
    }

    @GetMapping("/name/{categoryName}")
    public ResponseEntity<Optional<Category>> getCategoryName(@PathVariable String categoryName) {
        Optional<Category> category = repo.findByName(categoryName);
        if (!category.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Optional<Category>>(category, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        Category createdCategory = repo.save(category);
        if (createdCategory == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        Optional<Category> category = repo.findById(categoryId);
        if (!category.isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            repo.deleteById(categoryId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }
}

