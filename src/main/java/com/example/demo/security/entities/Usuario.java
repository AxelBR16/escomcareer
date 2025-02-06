package com.example.demo.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100) // Especifica el nombre de la columna, que no puede ser nula, y establece un tamaño de 100 caracteres.
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false, length = 100) // Similar al anterior, con las mismas restricciones.
    private String apellido;

    @NotEmpty(message = "El email es obligatorio")
    @Column(name = "email", nullable = false, unique = true, length = 150) // Columna de email, que debe ser única y con un tamaño máximo de 150 caracteres.
    private String email;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

}
