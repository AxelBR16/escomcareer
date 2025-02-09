package com.example.demo.security.repositories;

import com.example.demo.security.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AspiranteRepository extends JpaRepository<Aspirante, String> {
    Optional<Aspirante> findByEmail(String email);
}
