package com.example.demo.domain.repositories;

import com.example.demo.domain.dto.ProyectoConUsuarioDTO;
import com.example.demo.domain.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {

    List<Proyecto> findByEgresadoEmail(String email);
    @Query("SELECT p FROM Proyecto p WHERE p.egresado.email = :email AND p.estado = false ")
    List<Proyecto> findByEgresadoEmailAndValidadoFalse(@Param("email") String email);
    List<Proyecto> findByEstadoTrue();

    @Query("SELECT new com.example.demo.domain.dto.ProyectoConUsuarioDTO(p.id, p.nombre, p.descripcion, p.url, " +
            "p.estado, p.likes, p.dislikes, p.fecha, e.nombre, e.apellido, m.nombre) " +
            "FROM Proyecto p " +
            "JOIN p.egresado e " +
            "JOIN p.materia m " +
            "JOIN m.carrera c " +
            "WHERE c.id = :carreraId AND p.estado = true")
    List<ProyectoConUsuarioDTO> findProyectosPorCarreraConUsuario(@Param("carreraId") Integer carreraId);
    List<Proyecto> findByEstadoFalse();


}