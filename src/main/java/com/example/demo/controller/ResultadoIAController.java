package com.example.demo.controller;

import com.example.demo.domain.entities.ResultadoIA;
import com.example.demo.services.ResultadoIAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resultadoIA")
public class ResultadoIAController {
    @Autowired
    private ResultadoIAService resultadoIAService;

    @PostMapping
    public ResultadoIA guardarResultado(@RequestBody ResultadoIA resultadoIA) {
        return resultadoIAService.guardarResultado(resultadoIA);
    }
}

