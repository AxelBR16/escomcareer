package com.example.demo.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Pregunta {
    @Id
    private String id;

    @Column(nullable = false)
    private String texto;

    private String imagen_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id", nullable = false)
    @JsonBackReference
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "escala_id", nullable = false)
    @JsonBackReference
    private Escala escala;

}
