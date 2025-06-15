package com.example.demo.controller;

import com.example.demo.domain.dto.ResultadoIADTO;
import com.example.demo.domain.dto.ResultadoIARespuestaDTO;
import com.example.demo.domain.entities.ResultadoIA;
import com.example.demo.services.ResultadoIAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultadoIA")
public class ResultadoIAController {
    @Autowired
    private ResultadoIAService resultadoIAService;

    @PostMapping
    public ResponseEntity<?> guardarResultado(@RequestBody ResultadoIADTO dto) {
        ResultadoIA resultado = resultadoIAService.guardarResultado(dto);
        return ResponseEntity.ok(resultado);
    }


    @GetMapping("/por-email")
    public List<ResultadoIA> obtenerPorEmail(@RequestParam String email) {
        return resultadoIAService.obtenerResultadosPorEmail(email);
    }

    @GetMapping("/por-email-e-inventario")
    public List<ResultadoIARespuestaDTO> obtenerPorEmailEInventario(
            @RequestParam String email,
            @RequestParam Long inventarioId) {
        return resultadoIAService.obtenerResultadosPorEmailEInventario(email, inventarioId);
    }

}

