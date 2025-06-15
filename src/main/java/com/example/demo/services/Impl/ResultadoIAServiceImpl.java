package com.example.demo.services.Impl;

import com.example.demo.domain.entities.ResultadoIA;
import com.example.demo.domain.repositories.ResultadoIARepository;
import com.example.demo.services.ResultadoIAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultadoIAServiceImpl implements ResultadoIAService {

    @Autowired
    private ResultadoIARepository resultadoIARepository;

    public ResultadoIA guardarResultado(ResultadoIA resultadoIA) {
        return resultadoIARepository.save(resultadoIA);
    }
}
