package com.example.demo.services;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.dto.RespuestaDTO;
import com.example.demo.domain.entities.Respuesta;

import java.util.List;

public interface RespuestaService {
    public Respuesta guardarRespuesta(GuardarRespuestaDTO dto);
    List<Respuesta> obtenerRespuestasPorAspirante(String emailAspirante);
}
