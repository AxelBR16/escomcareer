package com.example.demo.services.Impl;

import com.example.demo.domain.dto.ResultadoIADTO;
import com.example.demo.domain.dto.ResultadoIARespuestaDTO;
import com.example.demo.domain.entities.Carrera;
import com.example.demo.domain.entities.Inventario;
import com.example.demo.domain.entities.ResultadoIA;
import com.example.demo.domain.repositories.CarreraRepository;
import com.example.demo.domain.repositories.InventarioRepository;
import com.example.demo.domain.repositories.ResultadoIARepository;
import com.example.demo.security.entities.Aspirante;
import com.example.demo.security.repositories.AspiranteRepository;
import com.example.demo.services.ResultadoIAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultadoIAServiceImpl implements ResultadoIAService {

    @Autowired
    private ResultadoIARepository resultadoIARepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @Autowired
    private AspiranteRepository aspiranteRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    public ResultadoIA guardarResultado(ResultadoIADTO dto) {
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email no puede ser null o vacío");
        }

        if (dto.getCarreraId() == null) {
            throw new IllegalArgumentException("El ID de carrera no puede ser null");
        }

        if (dto.getInventarioId() == null) {
            throw new IllegalArgumentException("El ID de inventario no puede ser null");
        }
        Aspirante aspirante = aspiranteRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        Inventario inventario = inventarioRepository.findById(dto.getInventarioId())
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));

        ResultadoIA resultado = new ResultadoIA();
        resultado.setAspirante(aspirante);
        resultado.setCarrera(carrera);
        resultado.setInventario(inventario);
        resultado.setPuntaje(dto.getPuntaje());

        return resultadoIARepository.save(resultado);
    }

    @Override
    public List<ResultadoIA> obtenerResultadosPorEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }

        return resultadoIARepository.findByAspiranteEmail(email);
    }

    @Override
    public List<ResultadoIARespuestaDTO> obtenerResultadosPorEmailEInventario(String email, Long inventarioId) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }
        if (inventarioId == null) {
            throw new IllegalArgumentException("El ID del inventario no puede ser null");
        }

        return resultadoIARepository.findByAspiranteEmailAndInventarioId(email, inventarioId).stream()
                .map(resultado -> new ResultadoIARespuestaDTO(
                        resultado.getCarrera().getId(),
                        resultado.getPuntaje()
                ))
                .toList();
    }


}
