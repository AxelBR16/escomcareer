package com.example.demo.domain.dto;

import lombok.Data;

@Data
public class RetroalimentacionDTO {
    private int general;       // experiencia general
    private int experiencias;  // opinión sobre experiencias/proyectos
    private int recomendacion; // si la recomendación fue útil
    private int sistema;

}
