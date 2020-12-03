package com.vslab.CatalogCmpApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable Long productId) {
        if (productId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Boolean> responseEntity;

        try {
            responseEntity = new ResponseEntity<Boolean>(catalogClient.deleteProduct(productId), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<Boolean>(false, tp.getStatusCode());
        }

        return responseEntity;
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            ResponseEntity<Category> responseEntityCat = new ResponseEntity<>(catalogClient.getCategory(product.getCategoryId()), HttpStatus.OK);
            ResponseEntity<Product> responseEntity = new ResponseEntity<>(catalogClient.addProduct(product), HttpStatus.CREATED);
            return responseEntity;
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<>(tp.getStatusCode());
        }
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "minPreis", required = false) Double minPreis,
            @RequestParam(value = "maxPreis", required = false) Double maxPreis
    ) {
        ResponseEntity<List<Product>> responseEntity;

        try {
            responseEntity = new ResponseEntity<>(catalogClient.getProducts(searchValue, maxPreis, minPreis), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<List<Product>>(tp.getStatusCode());
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

    @PostMapping("/category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        if (category == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Category> responseEntity = new ResponseEntity<>(catalogClient.addCategory(category), HttpStatus.CREATED);

        if (responseEntity == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId) {
        if (categoryId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Boolean> responseEntity;

        try {
            responseEntity = new ResponseEntity<Boolean>(catalogClient.deleteCategory(categoryId), HttpStatus.OK);
        } catch (HttpStatusCodeException tp) {
            return new ResponseEntity<Boolean>(false, tp.getStatusCode());
        }

        return responseEntity;
    }
}
