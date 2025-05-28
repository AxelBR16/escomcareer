package com.example.demo.domain.entities;

import com.example.demo.security.entities.Egresado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.GenerationType;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Experiencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "egresado_id")
    @JsonBackReference
    private Egresado egresado;
    private int likes = 0;
    private int dislikes = 0;
    private boolean estado = false;

}
