package com.example.demo.services.Impl;


import com.example.demo.domain.dto.RetroalimentacionDTO;
import com.example.demo.domain.dto.RetroalimentacionWithAspiranteDTO;
import com.example.demo.domain.entities.Retroalimentacion;
import com.example.demo.domain.repositories.RetroalimentacionRepository;
import com.example.demo.exceptions.RetroalimentacionDuplicadaException;
import com.example.demo.security.dtos.ResponseMessageDto;
import com.example.demo.security.entities.Aspirante;
import com.example.demo.security.repositories.AspiranteRepository;
import com.example.demo.services.RetroalimentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class RetroalimentacionServiceImpl implements RetroalimentacionService {

    @Autowired
    private final RetroalimentacionRepository retroalimentacionRepository;
    @Autowired
    private final AspiranteRepository aspiranteRepository;

    public RetroalimentacionServiceImpl(RetroalimentacionRepository retroalimentacionRepository, AspiranteRepository aspiranteRepository) {
        this.retroalimentacionRepository = retroalimentacionRepository;
        this.aspiranteRepository = aspiranteRepository;
    }

    @Override
    public Retroalimentacion save(RetroalimentacionDTO retroalimentacionDTO, String correo) {
        // Buscar al aspirante por correo
        Aspirante aspirante = aspiranteRepository.findByEmail(correo)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        // Verificar si ya existe una retroalimentación para este aspirante
        if (retroalimentacionRepository.existsByAspirante(aspirante)) {
            throw new RetroalimentacionDuplicadaException("Ya se ha enviado una retroalimentación para este aspirante.");
        }
        // Convertir DTO a entidad
        Retroalimentacion retroalimentacion = new Retroalimentacion();
        retroalimentacion.setGeneral(retroalimentacionDTO.getGeneral());
        retroalimentacion.setExperiencias(retroalimentacionDTO.getExperiencias());
        retroalimentacion.setRecomendacion(retroalimentacionDTO.getRecomendacion());
        retroalimentacion.setSistema(retroalimentacionDTO.getSistema());
        retroalimentacion.setFecha(LocalDate.now());

        // Asociar al aspirante
        retroalimentacion.setAspirante(aspirante);

        // Guardar la retroalimentación
        return retroalimentacionRepository.save(retroalimentacion);
    }

    public List<RetroalimentacionWithAspiranteDTO> getAllRetroalimentaciones() {
        // Obtener todas las retroalimentaciones
        List<Retroalimentacion> retroalimentaciones = retroalimentacionRepository.findAll();

        // Convertir cada retroalimentación a su correspondiente DTO usando map()
        return retroalimentaciones.stream()
                .map(r -> new RetroalimentacionWithAspiranteDTO(r))  // Usamos un lambda o constructor en lugar de un método de referencia
                .collect(Collectors.toList());  // Recoger los resultados en una lista
    }

}
