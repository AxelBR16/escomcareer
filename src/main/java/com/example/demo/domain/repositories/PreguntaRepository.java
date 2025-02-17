package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreguntaRepository extends JpaRepository<Pregunta, String> {
    @Query("SELECT p FROM Pregunta p WHERE p.inventario = :inventario")
    List<Pregunta> findByInventario(Inventario inventario);

}

