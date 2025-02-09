package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "inventario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pregunta> preguntas;
}
