package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Retroalimentacion;
import com.example.demo.security.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetroalimentacionRepository extends JpaRepository<Retroalimentacion, Long> {
    boolean existsByAspirante(Aspirante aspirante);
}
