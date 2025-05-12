package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Escala;
import com.example.demo.domain.entities.Resultado;
import com.example.demo.security.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultadoRepository extends JpaRepository<Resultado, Long> {
    List<Resultado> findByAspirante(Aspirante aspirante);
    boolean existsByAspiranteAndEscala(Aspirante aspirante, Escala escala);

}
