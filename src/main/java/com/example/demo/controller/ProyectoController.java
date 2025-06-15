package com.example.demo.controller;

import com.example.demo.domain.dto.ProyectoConUsuarioDTO;
import com.example.demo.domain.dto.ProyectoDTO;
import com.example.demo.domain.entities.Proyecto;
import com.example.demo.services.ProyectoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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
                proyectoDTO.getMateriaId());

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

    @GetMapping("/inactivos")
    public List<Proyecto> listarProyectosInactivos() {
        return proyectoService.obtenerProyectosInactivos();
    }

    @PutMapping("/aprobar/{id}")
    public ResponseEntity<Void> aprobarProyecto(@PathVariable Long id) {
        boolean resultado = proyectoService.aprobarProyecto(id);
        if (resultado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/rechazar/{id}")
    public ResponseEntity<Void> rechazarProyecto(@PathVariable Long id) {
        boolean resultado = proyectoService.rechazarProyecto(id);
        if (resultado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/votar")
    public Proyecto votarProyecto(@PathVariable Long id, @RequestParam String tipo) {
        return proyectoService.votar(id, tipo);
    }
}