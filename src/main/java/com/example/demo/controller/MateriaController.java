package com.example.demo.controller;

import com.example.demo.domain.dto.MateriaDTO;
import com.example.demo.services.MateriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/materias")
public class MateriaController {
    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping("/por-semestre")
    public List<MateriaDTO> obtenerMateriasPorSemestreYCarrera(
            @RequestParam int semestre,
            @RequestParam Long carreraId) {
        return materiaService.getMateriasPorSemestreYCarrera(semestre, carreraId);
    }

}
