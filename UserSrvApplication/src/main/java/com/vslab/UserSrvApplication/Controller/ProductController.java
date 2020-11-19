package com.vslab.UserSrvApplication.Controller;

import com.vslab.UserSrvApplication.Model.Product;
import com.vslab.UserSrvApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public ResponseEntity<Product> getCategory(@PathVariable Long productId) {
        Product product = repo.findOne(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //POST
}
