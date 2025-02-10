package com.example.demo.controller;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/preguntas")
@CrossOrigin(origins = {"*"})
public class PreguntaController {
    @Autowired
    private PreguntaService preguntaService;

    @GetMapping("/inventario/{inventarioId}")
    public List<Pregunta> obtenerPreguntasPorInventario(@PathVariable Long inventarioId) {
        Inventario inventario = new Inventario();
        inventario.setId(inventarioId);
        return preguntaService.obtenerPreguntasPorInventario(inventario);
    }
}
