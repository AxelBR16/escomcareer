package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExperienciaRequestDTO {
    private String descripcion;
    private String correo;
}
