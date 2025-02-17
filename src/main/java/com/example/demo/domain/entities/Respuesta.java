package com.example.demo.domain.entities;

import com.example.demo.security.entities.Aspirante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private int valor;

    @ManyToOne
    @JoinColumn(name = "id_pregunta", nullable = false)
    @JsonBackReference
    private Pregunta pregunta;

    @ManyToOne
    @JoinColumn(name = "id_aspirante", nullable = false)
    @JsonBackReference
    private Aspirante aspirante;

    public Respuesta(@NotNull(message = "El valor de la respuesta es obligatorio") int valor, Pregunta pregunta, Aspirante aspirante) {
    }
}


