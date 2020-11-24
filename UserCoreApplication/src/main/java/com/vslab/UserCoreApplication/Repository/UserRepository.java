package com.vslab.UserCoreApplication.Repository;

import com.vslab.UserCoreApplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    boolean existsById(Long id);

    User save(User user);

}
