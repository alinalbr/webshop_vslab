package com.vslab.ProductCoreApplication.Controller;

import java.util.List;
import java.util.Optional;

import com.vslab.ProductCoreApplication.Model.Product;
import com.vslab.ProductCoreApplication.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repo;

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(value = "searchValue", required = false) String searchValue,
            @RequestParam(value = "minPreis", required = false) Double minPreis,
            @RequestParam(value = "maxPreis", required = false) Double maxPreis
            ) {
                List<Product> products = repo.findAll();  

        if (searchValue != null) {
            products.stream().filter(product -> {
                return product.getName() == searchValue;
            });
        }
        if (minPreis != null) {
            products.stream().filter(product -> {
                return product.getPrice() >= minPreis;
            });
        }
        if (maxPreis != null) {
            products.stream().filter(product -> {
                return product.getPrice() <= maxPreis;
            });
        }

        return new ResponseEntity(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
        Optional<Product> product = repo.findById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(product, HttpStatus.OK); // TO DO product aufbereiten
        }
    }

    @PostMapping("")
    public ResponseEntity addProduct(@RequestBody Product product) {
        Product createdProduct = repo.save(product);
        if (createdProduct == null) {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(null, HttpStatus.CREATED);
        }   
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity deleteProductsMatchingCategory(@PathVariable Long categoryId) {
        // TO DO delete
        if (true) {// TO DO if id not found
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(null, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Long productId) {
        return new ResponseEntity(null, HttpStatus.OK); 
    }
}
