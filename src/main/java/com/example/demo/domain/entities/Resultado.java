package com.example.demo.domain.entities;

import com.example.demo.security.entities.Aspirante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resultado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int puntaje;

    @ManyToOne
    @JoinColumn(name = "aspirante_id", nullable = false)
    @JsonBackReference
    private Aspirante aspirante;

    @ManyToOne
    @JoinColumn(name = "escala_id", nullable = false)
    @JsonBackReference
    private Escala escala;
}
