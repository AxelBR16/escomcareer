package com.example.demo.security.repositories;

import com.example.demo.security.entities.Egresado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EgresadoRepository extends JpaRepository<Egresado, String> {
    Optional<Egresado> findByEmail(String email);
}
