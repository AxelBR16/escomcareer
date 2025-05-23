package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {
    List<Materia> findBySemestreAndCarreraId(int semestre, Long carreraId);
}
