package com.example.demo.services;

import com.example.demo.domain.dto.ResultadoIADTO;
import com.example.demo.domain.dto.ResultadoIARespuestaDTO;
import com.example.demo.domain.entities.ResultadoIA;
import java.util.List;


public interface ResultadoIAService {
    ResultadoIA guardarResultado(ResultadoIADTO dto);
    List<ResultadoIA> obtenerResultadosPorEmail(String email);
    List<ResultadoIARespuestaDTO> obtenerResultadosPorEmailEInventario(String email, Long inventarioId);

}
