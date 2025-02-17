package com.example.demo.services.Impl;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.dto.RespuestaDTO;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.entities.Respuesta;
import com.example.demo.domain.repositories.PreguntaRepository;
import com.example.demo.domain.repositories.RespuestaRepository;
import com.example.demo.security.entities.Aspirante;
import com.example.demo.security.repositories.AspiranteRepository;
import com.example.demo.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RespuestaServiceImp implements RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private AspiranteRepository aspiranteRepository;

    @Override
    public Respuesta guardarRespuesta(GuardarRespuestaDTO dto) {
        if (dto.getEmailAspirante() == null || dto.getEmailAspirante().isEmpty()) {
            throw new RuntimeException("El email del aspirante es nulo o vacío");
        }

        Pregunta pregunta = preguntaRepository.findById(dto.getId_Pregunta())
                .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

        Aspirante aspirante = aspiranteRepository.findByEmail(dto.getEmailAspirante())
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));


        Optional<Respuesta> respuestaExistente = respuestaRepository.findByPreguntaAndAspirante(pregunta, aspirante);

        Respuesta respuesta;
        if (respuestaExistente.isPresent()) {
            respuesta = respuestaExistente.get();
            respuesta.setValor(dto.getValor());
        } else {
            respuesta = new Respuesta();
            respuesta.setPregunta(pregunta);
            respuesta.setAspirante(aspirante);
            respuesta.setValor(dto.getValor());
        }


        return respuestaRepository.save(respuesta);
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorAspirante(String emailAspirante) {
        Aspirante aspirante = aspiranteRepository.findByEmail(emailAspirante)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        List<Respuesta> respuestas = respuestaRepository.findByAspirante(aspirante);

        return respuestas;
    }


}
