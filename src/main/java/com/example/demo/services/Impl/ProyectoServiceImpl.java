package com.example.demo.services.Impl;

import com.example.demo.domain.dto.ProyectoConUsuarioDTO;
import com.example.demo.domain.entities.Carrera;
import com.example.demo.domain.entities.Materia;
import com.example.demo.domain.entities.Proyecto;
import com.example.demo.domain.repositories.CarreraRepository;
import com.example.demo.domain.repositories.MateriaRepository;
import com.example.demo.domain.repositories.ProyectoRepository;
import com.example.demo.security.entities.Egresado;
import com.example.demo.security.repositories.EgresadoRepository;
import com.example.demo.services.ProyectoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoServiceImpl implements ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final EgresadoRepository egresadoRepository;
    private final MateriaRepository materiaRepository;
    private final CarreraRepository carreraRepository;


    public ProyectoServiceImpl(ProyectoRepository proyectoRepository,
                               EgresadoRepository egresadoRepository,
                               MateriaRepository materiaRepository, CarreraRepository carreraRepository) {
        this.proyectoRepository = proyectoRepository;
        this.egresadoRepository = egresadoRepository;
        this.materiaRepository = materiaRepository;
        this.carreraRepository = carreraRepository;
    }

    @Override
    public Proyecto guardarProyecto(Proyecto proyecto, String egresadoEmail, Long materiaId) {
        Optional<Egresado> egresadoOpt = egresadoRepository.findByEmail(egresadoEmail);
        Optional<Materia> materiaOpt = materiaRepository.findById(materiaId);

        if (!egresadoOpt.isPresent()) {
            throw new RuntimeException("Egresado no encontrado con email: " + egresadoEmail);
        }


        if (!materiaOpt.isPresent()) {
            throw new RuntimeException("Materia no encontrada con id: " + materiaId);
        }
        proyecto.setEgresado(egresadoOpt.get());
        proyecto.setMateria(materiaOpt.get());
        proyecto.setFecha(LocalDate.now());
        proyecto.setEstado(false);
        return proyectoRepository.save(proyecto);
    }
    @Override
    public List<Proyecto> obtenerProyectosPendientesPorUsuario(String egresadoEmail) {
        return proyectoRepository.findByEgresadoEmailAndValidadoFalse(egresadoEmail);
    }

    @Override
    public List<Proyecto> obtenerTodosLosProyectosPorUsuario(String egresadoEmail) {
        return proyectoRepository.findByEgresadoEmail(egresadoEmail);
    }

    @Override
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }

    @Override
    public List<Proyecto> obtenerVideosPorEstadoTrue() {
        return proyectoRepository.findByEstadoTrue();
    }

    @Override
    public List<ProyectoConUsuarioDTO> obtenerProyectosPorCarrera(Integer carreraId) {
        return proyectoRepository.findProyectosPorCarreraConUsuario(carreraId);
    }

    @Override
    public List<Proyecto> obtenerProyectosInactivos() {
        return proyectoRepository.findByEstadoFalse();
    }

    @Override
    public boolean aprobarProyecto(Long id) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (optionalProyecto.isPresent()) {
            Proyecto proyecto = optionalProyecto.get();
            proyecto.setEstado(true);  // Cambia estado a true
            proyectoRepository.save(proyecto);
            return true;
        }
        return false;
    }

    @Override
    public boolean rechazarProyecto(Long id) {
        Optional<Proyecto> optionalProyecto = proyectoRepository.findById(id);
        if (optionalProyecto.isPresent()) {
            proyectoRepository.deleteById(id);  // Borra el proyecto
            return true;
        }
        return false;
    }

    @Override
    public Proyecto votar(Long id, String tipo) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado con id: " + id));

        if ("like".equalsIgnoreCase(tipo)) {
            proyecto.setLikes(proyecto.getLikes() + 1);
        } else if ("dislike".equalsIgnoreCase(tipo)) {
            proyecto.setDislikes(proyecto.getDislikes() + 1);
        } else {
            throw new IllegalArgumentException("Tipo de voto no válido: " + tipo);
        }

        return proyectoRepository.save(proyecto);
    }
}