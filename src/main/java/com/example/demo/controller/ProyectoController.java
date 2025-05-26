package com.example.demo.controller;

import com.example.demo.domain.dto.ProyectoConUsuarioDTO;
import com.example.demo.domain.dto.ProyectoDTO;
import com.example.demo.domain.entities.Proyecto;
import com.example.demo.services.ProyectoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/proyectos")
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @PostMapping
    public ResponseEntity<String> crearProyecto(@RequestBody ProyectoDTO proyectoDTO) {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(proyectoDTO.getNombre());
        proyecto.setDescripcion(proyectoDTO.getDescripcion());
        proyecto.setUrl(proyectoDTO.getUrl());
        proyecto.setLikes(proyectoDTO.getLikes());
        proyecto.setDislikes(proyectoDTO.getDislikes());

        proyectoService.guardarProyecto(
                proyecto,
                proyectoDTO.getEgresadoEmail(),
                proyectoDTO.getMateriaId(),
                proyectoDTO.getCarreraId());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pendientes")
    public List<Proyecto> obtenerProyectosPendientes(@RequestParam String email) {
        return proyectoService.obtenerProyectosPendientesPorUsuario(email);
    }

    @GetMapping("/usuario")
    public List<Proyecto> obtenerProyectosPorUsuario(@RequestParam String email) {
        return proyectoService.obtenerTodosLosProyectosPorUsuario(email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/activos")
    public List<Proyecto> obtenerVideosActivos() {
        return proyectoService.obtenerVideosPorEstadoTrue();
    }

    @GetMapping()
    public List<ProyectoConUsuarioDTO> getProyectosPorCarreraConUsuario(@RequestParam Integer carreraId) {
        return proyectoService.obtenerProyectosPorCarrera(carreraId);
    }

}