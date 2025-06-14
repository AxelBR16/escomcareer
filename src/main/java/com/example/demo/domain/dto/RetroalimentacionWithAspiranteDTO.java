package com.example.demo.domain.dto;

import com.example.demo.domain.entities.Retroalimentacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RetroalimentacionWithAspiranteDTO {
    private Long id;
    private int general;
    private int experiencias;
    private int recomendacion;
    private int sistema;
    private LocalDate fecha;
    private String nombreAspirante;
    private String apellidoAspirante;

    public RetroalimentacionWithAspiranteDTO(Retroalimentacion retroalimentacion) {
        this.id = retroalimentacion.getId();
        this.general = retroalimentacion.getGeneral();
        this.experiencias = retroalimentacion.getExperiencias();
        this.recomendacion = retroalimentacion.getRecomendacion();
        this.sistema = retroalimentacion.getSistema();
        this.fecha = retroalimentacion.getFecha();
        this.nombreAspirante = retroalimentacion.getAspirante().getNombre();  // Asumiendo que tienes getNombre() en Aspirante
        this.apellidoAspirante = retroalimentacion.getAspirante().getApellido();  // Asumiendo que tienes getApellido() en Aspirante
    }
}
