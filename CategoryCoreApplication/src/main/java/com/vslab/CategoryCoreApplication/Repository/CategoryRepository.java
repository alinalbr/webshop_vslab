package com.vslab.UserSrvApplication.Repository;

import com.vslab.UserSrvApplication.Model.Category;
import com.vslab.UserSrvApplication.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findOne(Long categoryId);

}
