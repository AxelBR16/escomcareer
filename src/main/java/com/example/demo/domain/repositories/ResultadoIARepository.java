package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.ResultadoIA;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ResultadoIARepository extends JpaRepository<ResultadoIA, Long> {
    List<ResultadoIA> findByAspiranteEmail(String email);
    List<ResultadoIA> findByAspiranteEmailAndInventarioId(String email, Long inventarioId);
}
