package com.vslab.UserSrvApplication.Repository;

import com.vslab.UserSrvApplication.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
