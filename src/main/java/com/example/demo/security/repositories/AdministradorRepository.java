package com.example.demo.security.repositories;

import com.example.demo.security.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, String> {
    Optional<Administrador> findByEmail(String email);

    Optional<Administrador> findById(String id);

}
