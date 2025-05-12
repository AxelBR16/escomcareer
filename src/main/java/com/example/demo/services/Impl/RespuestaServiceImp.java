package com.example.demo.services.Impl;

import com.example.demo.domain.dto.GuardarRespuestaDTO;
import com.example.demo.domain.dto.RespuestaDTO;
import com.example.demo.domain.dto.ResultadoResumenDTO;
import com.example.demo.domain.entities.Escala;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.entities.Respuesta;
import com.example.demo.domain.entities.Resultado;
import com.example.demo.domain.repositories.EscalaRepository;
import com.example.demo.domain.repositories.PreguntaRepository;
import com.example.demo.domain.repositories.RespuestaRepository;
import com.example.demo.domain.repositories.ResultadoRepository;
import com.example.demo.security.entities.Aspirante;
import com.example.demo.security.repositories.AspiranteRepository;
import com.example.demo.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RespuestaServiceImp implements RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private AspiranteRepository aspiranteRepository;

    @Autowired
    private EscalaRepository escalaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

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

    @Override
    public Map<String, String> obtenerRespuestasPorAspiranteEInventario(String email, String inventario) {
        List<Respuesta> respuestas = obtenerRespuestasPorAspirante(email);

        if (respuestas.isEmpty()) {
            throw new NoSuchElementException("El aspirante con el correo " + email + " no tiene respuestas registradas.");
        }

        Map<String, String> respuestasFiltradas = new LinkedHashMap<>();

        for (Respuesta respuesta : respuestas) {
            String idPregunta = respuesta.getPregunta().getId(); // Ej: "inv1-001"
            if (idPregunta.startsWith(inventario)) {
                respuestasFiltradas.put(idPregunta, String.valueOf(respuesta.getValor()));
            }
        }

        if (respuestasFiltradas.isEmpty()) {
            throw new NoSuchElementException("No hay respuestas para el inventario " + inventario);
        }

        return respuestasFiltradas;
    }



    @Override
    public String obtenerRespuestaConIdMasAltoPorInventario(String email, String inventario) {
        List<Respuesta> respuestas = obtenerRespuestasPorAspirante(email);

        return respuestas.stream()
                .filter(r -> r.getPregunta().getId().startsWith(inventario + "-"))
                .map(r -> {
                    String[] partes = r.getPregunta().getId().split("-");
                    return partes.length > 1 ? partes[1] : null;
                })
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(Integer::parseInt))
                .orElse(null);
    }


    @Override
    public List<RespuestaDTO> obtenerRespuestasPorEscalaYCorreo(Long escalaId, String email) {
        return respuestaRepository.findByEscalaIdAndAspiranteEmail(escalaId, email);
    }

    @Override
    public Integer obtenerSumaValoresPorEscalaYCorreo(Long escalaId, String email) {
        Integer suma = respuestaRepository.sumarValoresPorEscalaYEmail(escalaId, email);
        if (suma == null) suma = 0;

        // Buscar aspirante
        var aspirante = aspiranteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        // Buscar escala
        var escala = escalaRepository.findById(escalaId)
                .orElseThrow(() -> new RuntimeException("Escala no encontrada"));

        // Guardar resultado
        Resultado resultado = new Resultado();
        resultado.setAspirante(aspirante);
        resultado.setEscala(escala);
        resultado.setPuntaje(suma);

        resultadoRepository.save(resultado);

        return suma;
    }

    @Override
    public void calcularYGuardarResultadosParaTodasLasEscalas(String email) {
        var aspirante = aspiranteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        for (long escalaId = 1; escalaId <= 12; escalaId++) {
            var escala = escalaRepository.findById(escalaId)
                    .orElseThrow(() -> new RuntimeException("Escala con id no encontrada"));
            Integer suma = respuestaRepository.sumarValoresPorEscalaYEmail(escalaId, email);
            if (suma == null) suma = 0;


            // Guardar el resultado (opcional: evitar duplicados si ya existe)
            Resultado resultado = new Resultado();
            resultado.setAspirante(aspirante);
            resultado.setEscala(escala);
            resultado.setPuntaje(suma);

            resultadoRepository.save(resultado);
        }
    }

    public void calcularYGuardarResultadoPorInventario(String emailAspirante, String inventario) {
        Aspirante aspirante = aspiranteRepository.findByEmail(emailAspirante)
                .orElseThrow(() -> new RuntimeException("Aspirante no encontrado"));

        // Determinar rango de escalas según inventario
        int inicioEscala, finEscala;

        switch (inventario.toLowerCase()) {
            case "inv1":
                inicioEscala = 1;
                finEscala = 12;
                break;
            case "inv2":
                inicioEscala = 13;
                finEscala = 26;
                break;
            default:
                throw new RuntimeException("Inventario no válido: " + inventario);
        }

        // Por cada escala en el rango, sumar respuestas y guardar si no existe resultado aún
        for (int escalaId = inicioEscala; escalaId <= finEscala; escalaId++) {
            Escala escala = escalaRepository.findById((long) escalaId)
                    .orElseThrow(() -> new RuntimeException("Escala no encontrada con ID "));

            // Verificar si ya existe resultado para esta escala y aspirante
            boolean yaExiste = resultadoRepository.existsByAspiranteAndEscala(aspirante, escala);
            if (yaExiste) continue; // Saltar si ya fue calculado

            // Obtener todas las respuestas del aspirante para preguntas de esta escala
            List<Respuesta> respuestas = respuestaRepository.findByAspiranteAndPregunta_Escala_Id(aspirante, escalaId);

            int puntaje = respuestas.stream().mapToInt(Respuesta::getValor).sum();

            Resultado resultado = new Resultado();
            resultado.setAspirante(aspirante);
            resultado.setEscala(escala);
            resultado.setPuntaje(puntaje);

            resultadoRepository.save(resultado);
        }
    }

    public List<ResultadoResumenDTO> obtenerResumenResultadosPorCorreo(String email) {
        Aspirante aspirante = aspiranteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aspirante con email " + email + " no encontrado"));

        List<Resultado> resultados = resultadoRepository.findByAspirante(aspirante);

        return resultados.stream()
                .map(r -> new ResultadoResumenDTO(r.getEscala().getId(), r.getPuntaje()))
                .collect(Collectors.toList());
    }

}
