package com.example.demo.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuardarRespuestaDTO {

    @NotNull(message = "El valor de la respuesta es obligatorio")
    private int valor;

    @NotNull(message = "El ID de la pregunta es obligatorio")
    private String id_Pregunta;

    @NotNull(message = "El email del aspirante es obligatorio")
    private String emailAspirante;
}
