package com.example.demo.services;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.entities.Respuesta;

import java.util.List;
import java.util.Map;

public interface RespuestaService {
    public Respuesta guardarRespuesta(GuardarRespuestaDTO dto);
    Map<String, String> obtenerRespuestasPorAspiranteEInventario(String email, String inventario);
    List<Respuesta> obtenerRespuestasPorAspirante(String emailAspirante);
    String obtenerRespuestaConIdMasAltoPorInventario(String email, String inventario);
}
