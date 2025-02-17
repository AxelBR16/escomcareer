package com.example.demo.security.entities;

import com.example.demo.domain.entities.Respuesta;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aspirante {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nombre", nullable = false, length = 100) // Especifica el nombre de la columna, que no puede ser nula, y establece un tamaño de 100 caracteres.
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100) // Similar al anterior, con las mismas restricciones.
    private String apellido;

    @NotEmpty(message = "El email es obligatorio")
    @Column(name = "email", nullable = false, unique = true, length = 150) // Columna de email, que debe ser única y con un tamaño máximo de 150 caracteres.
    private String email;

    @OneToMany(mappedBy = "aspirante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Respuesta> respuestas;

}
