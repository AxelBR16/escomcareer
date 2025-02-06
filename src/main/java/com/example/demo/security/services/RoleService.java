package com.example.demo.security.services;

import com.example.demo.security.entities.Role;
import com.example.demo.security.enums.RoleName;
import com.example.demo.security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public void save() {
        Role admin = new Role(RoleName.ROLE_ADMIN);
        Role aspirante = new Role(RoleName.ROLE_ASPIRANTE);
        Role egresado = new Role(RoleName.ROLE_EGRESADO);
        roleRepository.save(admin);
        roleRepository.save(aspirante);
        roleRepository.save(egresado);
    }

    public Optional<Role> getRoleByName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
