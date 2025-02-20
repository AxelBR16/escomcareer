package com.example.demo.services.Impl;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.repositories.PreguntaRepository;
import com.example.demo.services.PreguntaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    @Transactional
    public List<Pregunta> obtenerPreguntasPorInventario(Inventario inventario) {
        if (inventario == null || inventario.getId() == null) {
            throw new IllegalArgumentException("El inventario proporcionado es nulo o no tiene un ID válido.");
        }

        return Optional.ofNullable(preguntaRepository.findByInventario(inventario))
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(() -> new EntityNotFoundException("No se encontraron preguntas para el inventario con ID: " + inventario.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public Pregunta obtenerPreguntaPorId(String id) {
        return preguntaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada con ID: " + id));
    }


}
