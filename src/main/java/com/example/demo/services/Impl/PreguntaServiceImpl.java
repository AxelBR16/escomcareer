package com.example.demo.services.Impl;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.repositories.PreguntaRepository;
import com.example.demo.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Override
    @Transactional
    public List<Pregunta> obtenerPreguntasPorInventario(Inventario inventario) {
        return preguntaRepository.findByInventario(inventario);
    }
}
