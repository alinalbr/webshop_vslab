package com.vslab.UserSrvApplication.Repository;

import com.vslab.UserSrvApplication.Model.Product;
import com.vslab.UserSrvApplication.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findOne(Long productId);
}
