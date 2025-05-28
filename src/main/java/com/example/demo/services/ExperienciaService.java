package com.example.demo.services;

import com.example.demo.domain.dto.ExperienciaRequestDTO;
import com.example.demo.domain.dto.ExperienciaResponseDTO;
import com.example.demo.domain.dto.TrabajoRequestDTO;
import com.example.demo.domain.entities.Experiencia;
import com.example.demo.domain.entities.Habilidades;
import com.example.demo.domain.entities.Trabajo;

import java.util.List;

public interface ExperienciaService {
    Experiencia guardarExperiencia(ExperienciaRequestDTO dto);
    Trabajo guardarTrabajo(TrabajoRequestDTO dto);
    List<Habilidades> buscarPorNombre(String nombre);
    List<ExperienciaResponseDTO> getExperienciasPorCarrera(Long carreraId);
    List<Trabajo> getTrabajosAprobadosPorCarreraEgresado(Long carreraId);
    List<Experiencia> getExperienciasPendientes();
    void aprobarExperiencia(Long id);
    void rechazarExperiencia(Long id);
}
