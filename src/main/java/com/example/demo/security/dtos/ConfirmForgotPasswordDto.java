package com.example.demo.security.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmForgotPasswordDto {
    private String email;
    private String ConfirmationCode;
    private String password;
}
