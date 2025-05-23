package com.example.demo.services;

import com.example.demo.domain.dto.MateriaDTO;

import java.util.List;

public interface MateriaService {
    List<MateriaDTO> getMateriasPorSemestreYCarrera(int semestre, Long carreraId);
}
