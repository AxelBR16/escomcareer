package com.example.demo.security.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UsuarioDto {
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio")
    private String apellido;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

    @NotEmpty(message = "El email es obligatorio")
    private String email;

    private String role;

    private int carrera;
}

