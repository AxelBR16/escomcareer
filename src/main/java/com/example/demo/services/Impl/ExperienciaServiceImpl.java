package com.example.demo.services.Impl;

import com.example.demo.domain.dto.ExperienciaRequestDTO;
import com.example.demo.domain.dto.ExperienciaResponseDTO;
import com.example.demo.domain.dto.TrabajoRequestDTO;
import com.example.demo.domain.entities.Experiencia;
import com.example.demo.domain.entities.Habilidades;
import com.example.demo.domain.entities.Trabajo;
import com.example.demo.domain.repositories.ExperienciaRepository;
import com.example.demo.domain.repositories.HabilidadesRepository;
import com.example.demo.domain.repositories.TrabajoRepository;
import com.example.demo.security.entities.Egresado;
import com.example.demo.security.repositories.EgresadoRepository;
import com.example.demo.services.ExperienciaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

    private final ExperienciaRepository experienciaRepository;
    private final EgresadoRepository egresadoRepository;
    private final HabilidadesRepository habilidadesRepository;
    private final TrabajoRepository trabajoRepository;



    public ExperienciaServiceImpl(ExperienciaRepository experienciaRepository, EgresadoRepository egresadoRepository,  HabilidadesRepository habilidadesRepository, TrabajoRepository trabajoRepository) {
        this.experienciaRepository = experienciaRepository;
        this.egresadoRepository = egresadoRepository;
        this.habilidadesRepository = habilidadesRepository;
        this.trabajoRepository = trabajoRepository;
    }

    @Override
    public Experiencia guardarExperiencia(ExperienciaRequestDTO dto) {
        Egresado egresado = egresadoRepository.findByEmail(dto.getCorreo())
                .orElseThrow(() -> new RuntimeException("Egresado no encontrado con correo: " + dto.getCorreo()));

        Experiencia experiencia = new Experiencia();
        experiencia.setDescripcion(dto.getDescripcion());
        experiencia.setEgresado(egresado);
        experiencia.setEstado(false);
        experiencia.setFecha(LocalDate.now());
        return experienciaRepository.save(experiencia);
    }


    @Override
    public Trabajo guardarTrabajo(TrabajoRequestDTO dto) {
        Egresado egresado = egresadoRepository.findByEmail(dto.getCorreoEgresado())
                .orElseThrow(() -> new RuntimeException("Egresado no encontrado con correo: " + dto.getCorreoEgresado()));

        Trabajo trabajo = new Trabajo();
        trabajo.setDescripcion(dto.getDescripcion());
        trabajo.setPuesto(dto.getPuesto());
        trabajo.setSalario(dto.getSalario());
        trabajo.setEgresado(egresado);

        // Crear nueva colección para evitar modificar la colección original de Hibernate
        Set<Habilidades> habilidadesSet = new HashSet<>();

        if (dto.getHabilidades() != null) {
            for (String nombreHabilidad : dto.getHabilidades()) {
                Habilidades habilidad = habilidadesRepository.findByNombre(nombreHabilidad)
                        .orElseGet(() -> {
                            Habilidades nuevaHabilidad = new Habilidades();
                            nuevaHabilidad.setNombre(nombreHabilidad);
                            return habilidadesRepository.save(nuevaHabilidad);
                        });
                habilidadesSet.add(habilidad);
            }
        }

        trabajo.setHabilidades(habilidadesSet);  // Asigna colección nueva, no modifica la original

        return trabajoRepository.save(trabajo);
    }

    @Override
    public List<Habilidades> buscarPorNombre(String nombre) {
        return habilidadesRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<ExperienciaResponseDTO> getExperienciasPorCarrera(Long carreraId) {
        return experienciaRepository.findByEgresadoCarreraIdAndEstadoTrue(carreraId);
    }

    @Override
    public List<Trabajo> getTrabajosAprobadosPorCarreraEgresado(Long carreraId) {
        return trabajoRepository.findByEstadoTrueAndEgresadoCarreraId(carreraId);
    }
    @Override
    public List<Experiencia> getExperienciasPendientes() {
        return experienciaRepository.findByEstadoFalse();
    }

    @Override
    public void aprobarExperiencia(Long id) {
        Optional<Experiencia> optionalExperiencia = experienciaRepository.findById(id);
        if (optionalExperiencia.isPresent()) {
            Experiencia experiencia = optionalExperiencia.get();
            experiencia.setEstado(true);
            experienciaRepository.save(experiencia);
        } else {
            throw new RuntimeException("Experiencia no encontrada con id: " + id);
        }
    }

    public void rechazarExperiencia(Long id) {
        if (experienciaRepository.existsById(id)) {
            experienciaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Experiencia no encontrada con id: " + id);
        }
    }
}
