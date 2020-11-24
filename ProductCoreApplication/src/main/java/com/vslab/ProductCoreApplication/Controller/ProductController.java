package com.vslab.ProductCoreApplication.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            products = products.stream()
            .filter((Product product) -> {
                return product.getName().equals(searchValue);
            })
            .collect(Collectors.toList());
        }

        if (minPreis != null) {
            products = products.stream()
                .filter((Product product) -> {
                    return product.getPrice() >= minPreis;
                })
            .collect(Collectors.toList());
        }
        if (maxPreis != null) {
            products = products.stream()
            .filter((Product product) -> {
                return product.getPrice() <= maxPreis;
            })
            .collect(Collectors.toList());
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Optional<Product>> getProduct(@PathVariable Long productId) {
        Optional<Product> product = repo.findById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity<Optional<Product>>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Optional<Product>>(product, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        Product createdProduct = repo.save(product);
        if (createdProduct == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        }   
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteProductsMatchingCategory(@PathVariable Long categoryId) {
       List<Product> productsMatchingCategory = repo.findByCategoryId(categoryId);
        if (productsMatchingCategory.size() == 0) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            repo.deleteByCategoryId(categoryId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Optional<Product> product = repo.findById(productId);
        if (!product.isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            repo.deleteById(productId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }
}
