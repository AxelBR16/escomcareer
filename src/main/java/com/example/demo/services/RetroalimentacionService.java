package com.example.demo.services;

import com.example.demo.domain.dto.RetroalimentacionDTO;
import com.example.demo.domain.dto.RetroalimentacionWithAspiranteDTO;
import com.example.demo.domain.entities.Retroalimentacion;

import java.util.List;

public interface RetroalimentacionService {
    Retroalimentacion save(RetroalimentacionDTO retroalimentacionDTO, String correo);
    List<RetroalimentacionWithAspiranteDTO> getAllRetroalimentaciones();
}
