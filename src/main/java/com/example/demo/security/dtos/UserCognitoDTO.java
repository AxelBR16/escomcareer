package com.example.demo.security.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UserCognitoDTO {
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;

    @NotEmpty(message = "El email es obligatorio")
    private String email;
}
