package com.example.demo.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RespuestaDTO {
    private String idPregunta;
    private  int valor;
}
