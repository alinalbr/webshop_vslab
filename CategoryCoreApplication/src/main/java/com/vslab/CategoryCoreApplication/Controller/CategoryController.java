package com.vslab.CategoryCoreApplication.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @GetMapping("")		// GET /Category
    @JsonIgnoreProperties("productIds")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> category = repo.findAll();
        return new ResponseEntity<List<Category>>(category, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")		// GET /Category/{categoryId}
    @JsonIgnoreProperties({"productIds", "id"})
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable Long categoryId) {
        Optional<Category> category = repo.findById(categoryId);
        if (!category.isPresent()) {
            return new ResponseEntity<Optional<Category>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Optional<Category>>(category, HttpStatus.OK);
        }
    }

    @PostMapping("")					// POST /Category
    public ResponseEntity<Void> addCategory(@RequestBody String name) {
        Category categoryToBeAdded = new Category(name);
        Category createdCategory = repo.save(categoryToBeAdded);
        if (createdCategory == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }
    }


    @DeleteMapping("/{categoryId}")		// DELETE /Category/{categoryId}
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        Optional<Category> category = repo.findById(categoryId);
        if (!category.isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            repo.deleteById(categoryId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @PostMapping("/{categoryId}")					// POST /Category/{categoryId} new prod to category
    public ResponseEntity<Void> addProductToCategory(@RequestBody Long productId, @PathVariable Long categoryId) {
    	Optional<Category> category = repo.findById(categoryId);
        if (category.isPresent()) {
            category.get().addProduct(productId);
            repo.save(category.get());
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{categoryId}/{productId}")		// DELETE /Category/{categoryId} del product in cat. array
    public ResponseEntity<Void> deleteProductFromCategory(@PathVariable Long categoryId, @PathVariable Long productId) {
        Optional<Category> category = repo.findById(categoryId);
        if (category.isPresent()) {
            category.get().removeProduct(productId);
            repo.save(category.get());
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}

