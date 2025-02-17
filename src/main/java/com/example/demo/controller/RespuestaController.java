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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping("/obtenerPorAspirante")
    public ResponseEntity<?> obtenerRespuestasPorAspirante(@RequestBody ObtenerRespuestasDTO request) {
        try {
            List<Respuesta> respuestas = respuestaService.obtenerRespuestasPorAspirante(request.getEmail());

            // Verificar si el aspirante tiene respuestas
            if (respuestas.isEmpty()) {
                ResponseMessageDto errorMessage = new ResponseMessageDto(
                        "El aspirante con el correo " + request.getEmail() + " no tiene respuestas registradas."
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }

            // Mapear solo id_pregunta y valor a una lista de objetos simplificados
            List<Map<String, Object>> respuestasSimplificadas = respuestas.stream()
                    .map(respuesta -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id_pregunta", respuesta.getPregunta().getId());
                        map.put("valor", respuesta.getValor());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(respuestasSimplificadas);

        } catch (RuntimeException e) {
            ResponseMessageDto errorMessage = new ResponseMessageDto(
                    "Se produjo un error inesperado al procesar las respuestas para el aspirante con correo " + request.getEmail() + "."
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

}
