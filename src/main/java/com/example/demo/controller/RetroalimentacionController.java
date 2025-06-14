package com.example.demo.controller;

import com.example.demo.domain.dto.RetroalimentacionDTO;
import com.example.demo.domain.dto.RetroalimentacionWithAspiranteDTO;
import com.example.demo.domain.entities.Retroalimentacion;
import com.example.demo.exceptions.RetroalimentacionDuplicadaException;
import com.example.demo.services.RetroalimentacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/retroalimentacion")
public class RetroalimentacionController {
    private final RetroalimentacionService retroalimentacionService;

    public RetroalimentacionController(RetroalimentacionService retroalimentacionService) {
        this.retroalimentacionService = retroalimentacionService;
    }

    @PostMapping("/{correo}")
    public ResponseEntity<?> guardar(@PathVariable String correo, @RequestBody RetroalimentacionDTO retroalimentacionDTO) {
        try {
            // Llamar al servicio para guardar la retroalimentación
            Retroalimentacion retroalimentacion = retroalimentacionService.save(retroalimentacionDTO, correo);
            // Si todo sale bien, devolvemos la retroalimentación con código 201 (Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(retroalimentacion);
        } catch (RetroalimentacionDuplicadaException e) {
            // Si ocurre una excepción (retroalimentación duplicada), devolvemos el mensaje de error con código 400
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Retroalimentacion());
        }
    }
    @GetMapping
    public ResponseEntity<List<RetroalimentacionWithAspiranteDTO>> getAllRetroalimentaciones() {
        List<RetroalimentacionWithAspiranteDTO> retroalimentacionesDTO = retroalimentacionService.getAllRetroalimentaciones();
        return ResponseEntity.ok(retroalimentacionesDTO);  // Retorna la lista de DTOs con los datos del aspirante
    }
}
