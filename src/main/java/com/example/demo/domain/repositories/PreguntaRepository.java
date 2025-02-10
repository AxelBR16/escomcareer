package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PreguntaRepository extends CrudRepository<Pregunta, String> {
    List<Pregunta> findByInventario(Inventario inventario);
}
