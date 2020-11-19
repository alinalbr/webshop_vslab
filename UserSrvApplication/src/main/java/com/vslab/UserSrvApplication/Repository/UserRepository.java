package com.vslab.UserSrvApplication.Repository;
import com.vslab.UserSrvApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findOne(Long userId);

}