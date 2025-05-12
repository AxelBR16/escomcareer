package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultadoResumenDTO {
    private Long escalaId;
    private int puntaje;
}