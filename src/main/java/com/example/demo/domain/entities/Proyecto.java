    package com.example.demo.domain.entities;

    import com.example.demo.security.entities.Egresado;
    import com.fasterxml.jackson.annotation.JsonBackReference;
    import jakarta.persistence.*;
    import lombok.Data;

    import java.time.LocalDate;

    @Entity
    @Data
    public class Proyecto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nombre;
        private String descripcion;
        private Boolean estado;
        private String url;
        private int likes = 0;
        private int dislikes = 0;


        private LocalDate fecha;


        @ManyToOne
        @JoinColumn(name = "egresado_id")
        @JsonBackReference
        private Egresado egresado;

        @ManyToOne
        @JoinColumn(name = "materia_id")
        @JsonBackReference
        private Materia materia;

        @ManyToOne
        @JoinColumn(name = "carrera_id")
        @JsonBackReference
        private Carrera carrera;

    }
