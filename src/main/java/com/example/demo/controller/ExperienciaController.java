package com.example.demo.controller;

import com.example.demo.domain.dto.ExperienciaRequestDTO;
import com.example.demo.domain.dto.ExperienciaResponseDTO;
import com.example.demo.domain.dto.TrabajoRequestDTO;
import com.example.demo.domain.entities.Experiencia;
import com.example.demo.domain.entities.Habilidades;
import com.example.demo.domain.entities.Trabajo;
import com.example.demo.services.ExperienciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experiencias")
public class ExperienciaController {
    private final ExperienciaService experienciaService;

    public ExperienciaController(ExperienciaService experienciaService) {
        this.experienciaService = experienciaService;
    }

    @PostMapping()
    public ResponseEntity<Experiencia> guardarExperiencia(@RequestBody ExperienciaRequestDTO dto) {
        Experiencia experienciaGuardada = experienciaService.guardarExperiencia(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(experienciaGuardada);
    }

    @PostMapping("/trabajo")
    public ResponseEntity<Trabajo> guardarTrabajo(@RequestBody TrabajoRequestDTO dto) {
        Trabajo trabajoGuardado = experienciaService.guardarTrabajo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(trabajoGuardado);
    }

    @GetMapping("/habilidades")
    public List<Habilidades> buscarHabilidades(@RequestParam String query) {
        return experienciaService.buscarPorNombre(query);
    }

    @GetMapping("/por-carrera/{carreraId}")
    public List<ExperienciaResponseDTO> obtenerPorCarrera(@PathVariable Long carreraId) {
        return experienciaService.getExperienciasPorCarrera(carreraId);
    }

    @GetMapping("trabajo/{carreraId}")
    public List<Trabajo> obtenerTrabajosPorCarreraEgresado(@PathVariable Long carreraId) {
        return experienciaService.getTrabajosAprobadosPorCarreraEgresado(carreraId);
    }
    @GetMapping("/pendientes")
    public List<Experiencia> obtenerPendientes() {
        return experienciaService.getExperienciasPendientes();
    }

    @PutMapping("/aprobar/{id}")
    public ResponseEntity<Void> aprobarExperiencia(@PathVariable Long id) {
        experienciaService.aprobarExperiencia(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rechazar/{id}")
    public ResponseEntity<Void> rechazarExperiencia(@PathVariable Long id) {
        experienciaService.rechazarExperiencia(id);
        return ResponseEntity.noContent().build();
    }
}
