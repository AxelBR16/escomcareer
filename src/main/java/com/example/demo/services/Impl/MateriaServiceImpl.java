package com.example.demo.services.Impl;

import com.example.demo.domain.dto.MateriaDTO;
import com.example.demo.domain.repositories.MateriaRepository;
import com.example.demo.services.MateriaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MateriaServiceImpl implements MateriaService {
    private final MateriaRepository materiaRepository;

    public MateriaServiceImpl(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Override
    public List<MateriaDTO> getMateriasPorSemestreYCarrera(int semestre, Long carreraId) {
        return materiaRepository.findBySemestreAndCarreraId(semestre, carreraId)
                .stream()
                .map(m -> new MateriaDTO(
                        m.getId(),
                        m.getNombre(),
                        m.getDescripcion(),
                        m.getSemestre(),
                        m.getCarrera().getId()
                ))
                .toList();
    }

}
