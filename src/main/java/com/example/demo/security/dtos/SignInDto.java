package com.example.demo.security.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignInDto {
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

}
