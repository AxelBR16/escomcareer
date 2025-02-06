package com.example.demo.security.dtos;

import com.example.demo.security.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio")
    private String apellido;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

    @NotEmpty(message = "El email es obligatorio")
    private String email;

    private Role role;
}
