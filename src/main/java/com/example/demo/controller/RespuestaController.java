package com.example.demo.controller;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.dto.ObtenerRespuestasDTO;
import com.example.demo.domain.entities.Respuesta;
import com.example.demo.security.dtos.ResponseMessageDto;
import com.example.demo.services.RespuestaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @PostMapping("/guardar")
    public ResponseEntity<Respuesta> guardarRespuesta(@Valid @RequestBody GuardarRespuestaDTO dto) {
        Respuesta nuevaRespuesta = respuestaService.guardarRespuesta(dto);
        return ResponseEntity.ok(nuevaRespuesta);
    }

    @GetMapping("/obtenerPorAspiranteYInventario/{email}/{inventario}")
    public ResponseEntity<?> obtenerPorAspiranteYInventario(
            @PathVariable String email,
            @PathVariable String inventario) {

        try {
            Map<String, String> respuestasFiltradas =
                    respuestaService.obtenerRespuestasPorAspiranteEInventario(email, inventario);
            return ResponseEntity.ok(respuestasFiltradas);

        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessageDto(e.getMessage()));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDto("Error interno al procesar las respuestas."));
        }
    }


    @GetMapping("/obtenerRespuestaConIdMasAlto/{email}/{inventario}")
    public ResponseEntity<?> obtenerRespuestaConIdMasAltoPorInventario(
            @PathVariable String email,
            @PathVariable String inventario) {

        try {
            String respuesta = respuestaService.obtenerRespuestaConIdMasAltoPorInventario(email, inventario);

            if (respuesta == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessageDto("No se encontró una respuesta en el inventario " + inventario + " para el correo " + email));
            }
            int numeroq = Integer.parseInt(respuesta);
            return ResponseEntity.ok(numeroq);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDto("Error al obtener la respuesta con ID más alto del inventario."));
        }
    }


}
