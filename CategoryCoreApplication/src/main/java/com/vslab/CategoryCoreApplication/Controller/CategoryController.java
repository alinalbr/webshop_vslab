package com.vslab.UserSrvApplication.Controller;

import com.vslab.UserSrvApplication.Model.Category;
import com.vslab.UserSrvApplication.Model.User;
import com.vslab.UserSrvApplication.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository repo;

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        Category category = repo.findOne(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}

