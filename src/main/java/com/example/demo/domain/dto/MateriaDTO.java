package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MateriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int semestre;
    private int carreraId;
}
