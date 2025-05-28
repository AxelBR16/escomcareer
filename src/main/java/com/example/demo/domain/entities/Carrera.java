package com.example.demo.domain.entities;

import com.example.demo.security.entities.Egresado;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    @JsonManagedReference
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<Egresado> egresados;

        public Carrera(String nombre, String descripcion, String imagenUrl) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.imagenUrl = imagenUrl;
        }
    @JsonManagedReference
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL)
    private List<Materia> materias;




}
