package com.vslab.CatalogCmpApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class CatalogController {
    @Autowired
    private  CatalogClient catalogClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        if (productId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // request product from core service with catalogClient
        ResponseEntity<Product> responseEntity;

        try {
            responseEntity = new ResponseEntity<>(catalogClient.getProduct(productId), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<Product>(tp.getStatusCode());
        }

        return responseEntity;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
        if (categoryId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        // request category from core service with catalogClient
        ResponseEntity<Category> responseEntity;

        try {
            responseEntity = new ResponseEntity<>(catalogClient.getCategory(categoryId), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<Category>(tp.getStatusCode());
        }

        return responseEntity;
    }
}
