package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrabajoRequestDTO {
    private String descripcion;
    private String puesto;
    private Double salario;
    private String correoEgresado;
    private List<String> habilidades;
}