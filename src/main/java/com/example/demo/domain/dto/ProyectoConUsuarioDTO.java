package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ProyectoConUsuarioDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String url;
    private Boolean estado;
    private int likes;
    private int dislikes;
    private LocalDate fecha;
    private String nombreEgresado;
    private String apellidoEgresado;
    private String nombreMateria;
}
