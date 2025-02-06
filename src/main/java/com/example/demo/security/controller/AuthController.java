package com.example.demo.security.controller;

import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.UserNotConfirmedException;
import com.amazonaws.services.cognitoidp.model.UsernameExistsException;
import com.example.demo.security.dtos.AuthResponseDto;
import com.example.demo.security.dtos.ResponseMessageDto;
import com.example.demo.security.dtos.SignInDto;
import com.example.demo.security.dtos.SignUpDto;
import com.example.demo.security.entities.Usuario;
import com.example.demo.security.services.UserDetailsServiceImpl;
import com.example.demo.security.services.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseMessageDto> signUpUser(@RequestBody @Valid SignUpDto signUpDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new ResponseMessageDto(bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
            usuarioService.signUpUsuario(signUpDto);
            return ResponseEntity.ok(new ResponseMessageDto("Se ha registrado el usuario con éxito"));
        } catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessageDto("El usuario ya existe. Por favor, intenta con otro correo o inicia sesión."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessageDto("Ocurrió un error al registrar el usuario"));
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signInUser(@RequestBody @Valid SignInDto signInDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new AuthResponseDto(null, null, bindingResult.getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
        }
        try {
        String accessToken = usuarioService.singInUser(signInDto);
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInDto.getEmail());

        // Obtener el único rol del usuario
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


    @GetMapping("/user-details")
    public Optional<Usuario> getUserDetails() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        return usuarioService.getUsuarioByEmail(email);
    }


}
