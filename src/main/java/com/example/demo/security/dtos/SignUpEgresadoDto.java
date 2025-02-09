package com.example.demo.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpEgresadoDto extends UsuarioDto {
    private int carrera;
}
