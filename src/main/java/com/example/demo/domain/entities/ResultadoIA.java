package com.example.demo.domain.entities;
import com.example.demo.security.entities.Aspirante;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "resultado_ia")
public class ResultadoIA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_aspirante", nullable = false)
    @JsonBackReference
    private Aspirante aspirante;

    @Column(name = "puntaje", nullable = false)
    private Double puntaje;

    @ManyToOne
    @JoinColumn(name = "carrera_id", nullable = false)
    @JsonBackReference
    private Carrera carrera;

}
