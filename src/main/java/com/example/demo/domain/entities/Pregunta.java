package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    private String imagen_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "escala_id", nullable = false)
    private Escala escala;
}
