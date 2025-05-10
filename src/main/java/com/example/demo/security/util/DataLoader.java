package com.example.demo.security.util;


import com.example.demo.domain.entities.Carrera;
import com.example.demo.domain.repositories.CarreraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    private CarreraRepository carreraRepository;

    public DataLoader(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public void run(String... args) {
        if (carreraRepository.count() == 0) { // Solo si está vacía
            List<Carrera> carreras = List.of(
                    new Carrera("Ingeniería en Sistemas Computacionales", "Formar ingenieros en sistemas computacionales de sólida preparación científica y tecnológica en los ámbitos del desarrollo de software y hardware, que propongan, analicen, diseñen, desarrollen, implementen y gestionen sistemas computacionales.", ""),
                    new Carrera("Ingeniería en Inteligencia Artificial", "Formar expertos capaces de desarrollar sistemas inteligentes utilizando diferentes metodologías en las diferentes etapas de desarrollo y aplicando algoritmos en áreas como aprendizaje de máquina, procesamiento automático de lenguaje natural, visión artificial y modelos bioinspirados.", ""),
                    new Carrera("Licenciatura en Ciencia de Datos", "Formar expertos capaces de extraer conocimiento implícito y complejo, potencialmente útil a partir de grandes conjuntos de datos, utilizando métodos de inteligencia artificial, aprendizaje de máquina, estadística, sistemas de bases de datos y modelos matemáticos sobre comportamientos probables, para apoyar la toma de decisiones de alta dirección." , "")
            );
            carreraRepository.saveAll(carreras);
            System.out.println("✅ Carreras agregadas correctamente.");
        }

    }
}
