package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Materia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private int semestre;

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Proyecto> proyectos;
    
    @ManyToOne
    @JoinColumn(name = "carrera_id", nullable = false)

    private Carrera carrera;
}
