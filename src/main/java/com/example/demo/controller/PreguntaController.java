package com.example.demo.controller;

import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.security.dtos.ResponseMessageDto;
import com.example.demo.services.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPregunta(@PathVariable String id) {
        try {
            Pregunta pregunta = preguntaService.obtenerPreguntaPorId(id);
            return ResponseEntity.ok(pregunta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessageDto("La pregunta con el ID proporcionado no existe."));
        }
    }

}
