package com.vslab.UserCoreApplication.Repository;
import com.vslab.UserCoreApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOne(Long userId);
    User findByUsername(String username);

}