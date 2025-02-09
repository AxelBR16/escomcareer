package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity

public class Escala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "escala", cascade = CascadeType.ALL)
    private List<Pregunta> preguntas;
}
