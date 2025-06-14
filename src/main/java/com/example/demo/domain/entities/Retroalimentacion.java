package com.example.demo.domain.entities;

import com.example.demo.security.entities.Aspirante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Retroalimentacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int general;       // experiencia general
    private int experiencias;  // opinión sobre experiencias/proyectos
    private int recomendacion; // si la recomendación fue útil
    private int sistema;       // si recomendaría el sistema

    private LocalDate fecha;

    @OneToOne
    @JoinColumn(name = "aspirante_id", unique = true)
    @JsonBackReference
    private Aspirante aspirante;
}