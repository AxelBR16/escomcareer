package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ExperienciaResponseDTO {
    private Long id;
    private String descripcion;
    private LocalDate fecha;
    private String nombreEgresado;
    private String apellidoEgresado;
    private int likes;
    private int dislikes;
}
