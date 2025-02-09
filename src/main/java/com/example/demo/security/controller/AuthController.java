package com.example.demo.security.controller;

import com.amazonaws.services.cognitoidp.model.*;
import com.example.demo.security.dtos.*;
import com.example.demo.security.entities.Administrador;
import com.example.demo.security.services.UserDetailsServiceImpl;
import com.example.demo.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessageDto> signUpUser(@RequestBody @Valid UsuarioDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.signUpUsuario(signUpDto);
            return ResponseEntity.ok(new ResponseMessageDto("Se ha registrado el usuario con éxito"));
        } catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessageDto("El usuario ya existe. Por favor, intenta con otro correo o inicia sesión."));
        }
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signInUser(@RequestBody @Valid SignInDto signInDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new AuthResponseDto(null, null, bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
        String accessToken = userService.singInUser(signInDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInDto.getEmail());

        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER"); // Valor por defecto si no tiene rol asignado

        // Autenticar al usuario en el contexto de seguridad
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new AuthResponseDto(accessToken, role, "Inicio de sesión exitoso"));}
        catch (
                NotAuthorizedException e) {
            String errorMessage = "Usuario o contraseña incorrectos.";
            return new ResponseEntity<>(new AuthResponseDto(null, null, errorMessage), HttpStatus.BAD_REQUEST);
        } catch (UserNotConfirmedException e) {
            // Manejar el error cuando el usuario no está confirmado
            String errorMessage = "Usuario no confirmado. Por favor, revisa tu correo electrónico para confirmar tu cuenta.";
            return new ResponseEntity<>(new AuthResponseDto(null, null, errorMessage), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new AuthResponseDto(null, null, "Error en el inicio de sesión: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String accessToken) {
        userService.signOutUser(accessToken);
        return ResponseEntity.ok(new ResponseMessageDto("Cierre de sesión exitoso"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        boolean isEmailRegistered = userService.isEmailRegistered(forgotPasswordDto.getEmail());

        if (!isEmailRegistered) {
            return new ResponseEntity<>(new ResponseMessageDto("El correo electrónico no está registrado."), HttpStatus.BAD_REQUEST);
        }
        try {
            userService.forgotPassword(forgotPasswordDto);
            return ResponseEntity.ok(new ResponseMessageDto("Código de recuperación enviado"));
        } catch (LimitExceededException ex) {
        return new ResponseEntity<>(new ResponseMessageDto("Has excedido el número de intentos permitidos. Por favor, intenta más tarde."), HttpStatus.TOO_MANY_REQUESTS);
        }

    }

    @PostMapping("/confirm-forgotpassword")
    public ResponseEntity<?> confirmForgotPassword(@RequestBody ConfirmForgotPasswordDto confirmForgotPasswordDto) {
        try {
            userService.confirmForgotPassword(confirmForgotPasswordDto);
            return ResponseEntity.ok("Contraseña restablecida exitosamente");
        } catch (LimitExceededException e) {
            return new ResponseEntity<>(new ResponseMessageDto("Has excedido el límite de intentos. Intenta nuevamente después de un tiempo."), HttpStatus.BAD_REQUEST);
        } catch (ExpiredCodeException e) {
            return new ResponseEntity<>(new ResponseMessageDto("El código de verificación proporcionado ha expirado. Solicita un nuevo código."), HttpStatus.BAD_REQUEST);
        } catch (CodeMismatchException e) {
            return new ResponseEntity<>(new ResponseMessageDto("El código de verificación proporcionado no es válido. Intenta nuevamente."), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseMessageDto("Hubo un error al intentar procesar tu solicitud. Intenta nuevamente."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
