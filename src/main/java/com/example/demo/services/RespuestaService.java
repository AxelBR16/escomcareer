package com.example.demo.services;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.dto.RespuestaDTO;
import com.example.demo.domain.dto.ResultadoResumenDTO;
import com.example.demo.domain.entities.Respuesta;


import java.util.List;
import java.util.Map;

public interface RespuestaService {
    Respuesta guardarRespuesta(GuardarRespuestaDTO dto);
    Map<String, String> obtenerRespuestasPorAspiranteEInventario(String email, String inventario);
    List<Respuesta> obtenerRespuestasPorAspirante(String emailAspirante);
    String obtenerRespuestaConIdMasAltoPorInventario(String email, String inventario);
    List<RespuestaDTO> obtenerRespuestasPorEscalaYCorreo(Long escalaId, String email) ;
    Integer obtenerSumaValoresPorEscalaYCorreo(Long escalaId, String email);
    void calcularYGuardarResultadosParaTodasLasEscalas(String email);
    void calcularYGuardarResultadoPorInventario(String emailAspirante, String inventario);
    List<ResultadoResumenDTO> obtenerResumenResultadosPorCorreo(String email);
    void guardarMultiplesRespuestas(List<GuardarRespuestaDTO> respuestasDTO);
}
