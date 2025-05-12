package com.example.demo.domain.repositories;

import com.example.demo.domain.dto.RespuestaDTO;
import com.example.demo.domain.entities.Pregunta;
import com.example.demo.domain.entities.Respuesta;
import com.example.demo.security.entities.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    List<Respuesta> findByAspirante(Aspirante aspirante);;
    Optional<Respuesta> findByPreguntaAndAspirante(Pregunta pregunta, Aspirante aspirante);

    @Query("SELECT new com.example.demo.domain.dto.RespuestaDTO(r.pregunta.id, r.valor) " +
            "FROM Respuesta r " +
            "WHERE r.pregunta.escala.id = :escalaId AND r.aspirante.email = :email")
    List<RespuestaDTO> findByEscalaIdAndAspiranteEmail(@Param("escalaId") Long escalaId,
                                                       @Param("email") String email);

    @Query("SELECT SUM(r.valor) " +
            "FROM Respuesta r " +
            "WHERE r.pregunta.escala.id = :escalaId AND r.aspirante.email = :email")
    Integer sumarValoresPorEscalaYEmail(@Param("escalaId") Long escalaId,
                                        @Param("email") String email);
    List<Respuesta> findByAspiranteAndPregunta_Escala_Id(Aspirante aspirante, int escalaId);

}
