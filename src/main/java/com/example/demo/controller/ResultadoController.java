package com.example.demo.controller;

import com.example.demo.domain.dto.ResultadoResumenDTO;
import com.example.demo.domain.entities.Resultado;
import com.example.demo.services.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultados")
public class ResultadoController {
    @Autowired
    private RespuestaService respuestaService;

    @GetMapping("/calcular-resultados")
    public ResponseEntity<String> calcularResultadosPorEmail(@RequestParam String email) {
        respuestaService.calcularYGuardarResultadosParaTodasLasEscalas(email);
        return ResponseEntity.ok("Resultados calculados y guardados correctamente.");
    }

    @GetMapping("/calcular-resultados/{inventario}")
    public ResponseEntity<String> calcularResultado(@RequestParam String email, @PathVariable String inventario) {
        respuestaService.calcularYGuardarResultadoPorInventario(email, inventario);
        return ResponseEntity.ok("Resultados guardados correctamente para " + inventario);
    }

    @GetMapping
    public ResponseEntity<List<ResultadoResumenDTO>> obtenerResumenResultados(@RequestParam String email) {
        List<ResultadoResumenDTO> resumen = respuestaService.obtenerResumenResultadosPorCorreo(email);
        return ResponseEntity.ok(resumen);
    }
}
