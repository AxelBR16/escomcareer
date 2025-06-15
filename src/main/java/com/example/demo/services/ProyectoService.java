package com.example.demo.services;

import com.example.demo.domain.dto.ProyectoConUsuarioDTO;
import com.example.demo.domain.entities.Proyecto;

import java.util.List;

public interface ProyectoService {
    Proyecto guardarProyecto(Proyecto proyecto, String egresadoEmail, Long materiaId);
    List<Proyecto> obtenerProyectosPendientesPorUsuario(String egresadoEmail);
    List<Proyecto> obtenerTodosLosProyectosPorUsuario(String egresadoEmail);
    void eliminarProyecto(Long id);
    List<Proyecto> obtenerVideosPorEstadoTrue();
    List<ProyectoConUsuarioDTO> obtenerProyectosPorCarrera(Integer carreraId);
    List<Proyecto> obtenerProyectosInactivos();
    boolean aprobarProyecto(Long id);
    boolean rechazarProyecto(Long id);
    Proyecto votar(Long id, String tipo);
}
