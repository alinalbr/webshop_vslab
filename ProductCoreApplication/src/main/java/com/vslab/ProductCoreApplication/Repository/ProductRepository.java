package com.vslab.ProductCoreApplication.Repository;

import java.util.List;
import java.util.Optional;

import com.vslab.ProductCoreApplication.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Product save(Product product);
}
