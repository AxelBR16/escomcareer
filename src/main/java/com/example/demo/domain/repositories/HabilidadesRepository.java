package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Habilidades;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface HabilidadesRepository extends JpaRepository<Habilidades, Long> {
    Optional<Habilidades> findByNombre(String nombre);
    List<Habilidades> findByNombreContainingIgnoreCase(String nombre);

}
