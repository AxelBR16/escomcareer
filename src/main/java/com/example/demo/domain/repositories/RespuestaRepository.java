package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.entities.Respuesta;
import com.example.demo.security.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    List<Respuesta> findByAspirante(Aspirante aspirante);;
    Optional<Respuesta> findByPreguntaAndAspirante(Pregunta pregunta, Aspirante aspirante);

}
