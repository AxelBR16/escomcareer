package com.example.demo.services;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;

import java.util.List;

public interface PreguntaService {
    List<Pregunta> obtenerPreguntasPorInventario(Inventario inventario);
}
