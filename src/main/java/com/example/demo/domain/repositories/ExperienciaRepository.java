package com.example.demo.domain.repositories;

import com.example.demo.domain.dto.ExperienciaResponseDTO;
import com.example.demo.domain.entities.Experiencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExperienciaRepository  extends JpaRepository<Experiencia, Long> {
    List<Experiencia> findByEgresadoCarreraId(Long carreraId);
    @Query("SELECT new com.example.demo.domain.dto.ExperienciaResponseDTO(" +
            "e.id, e.descripcion, e.fecha, eg.nombre, eg.apellido, e.likes, e.dislikes) " +
            "FROM Experiencia e JOIN e.egresado eg " +
            "WHERE eg.carrera.id = :carreraId AND e.estado = true")
    List<ExperienciaResponseDTO> findByEgresadoCarreraIdAndEstadoTrue(@Param("carreraId") Long carreraId);
    List<Experiencia> findByEstadoFalse();
}
