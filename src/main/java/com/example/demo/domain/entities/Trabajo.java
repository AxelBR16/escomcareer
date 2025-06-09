package com.example.demo.domain.entities;

import com.example.demo.security.entities.Egresado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String puesto;
    private Double salario;

    @ManyToOne
    @JoinColumn(name = "egresado_id")
    @JsonBackReference
    private Egresado egresado;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "trabajo_habilidad",
            joinColumns = @JoinColumn(name = "trabajo_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    @JsonManagedReference
    private Set<Habilidades> habilidades = new HashSet<>();
    private boolean estado = false;
}
