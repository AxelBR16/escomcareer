package com.example.demo.security.repositories;

import com.example.demo.security.entities.Role;
import com.example.demo.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}
