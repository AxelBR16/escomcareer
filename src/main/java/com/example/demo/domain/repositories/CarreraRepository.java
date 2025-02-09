package com.example.demo.domain.repositories;

import com.example.demo.domain.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
}
