package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabajoRepository extends JpaRepository<Trabajo, Long> {
    List<Trabajo> findByEstadoTrueAndEgresadoCarreraId(Long carreraId);
    List<Trabajo> findByEstadoFalse();
}
