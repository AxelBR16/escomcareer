package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
}
