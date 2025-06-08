package com.example.demo.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ProyectoDTO {
    private String nombre;
    private String descripcion;
    private String estado;
    private String url;
    private int likes;
    private int dislikes;
    private String egresadoEmail;
    private Long materiaId;
}