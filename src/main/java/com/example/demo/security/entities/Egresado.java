package com.example.demo.security.entities;
import com.example.demo.domain.entities.Carrera;
import com.example.demo.domain.entities.Experiencia;
import com.example.demo.domain.entities.Trabajo;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Egresado {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @OneToMany(mappedBy = "egresado", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Trabajo> trabajos;

    @OneToMany(mappedBy = "egresado", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Experiencia> experiencias;


}
