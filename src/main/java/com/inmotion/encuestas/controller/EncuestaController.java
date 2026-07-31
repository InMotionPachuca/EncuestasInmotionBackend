package com.inmotion.encuestas.controller;

import com.inmotion.encuestas.dto.EncuestaRequestDTO;
import com.inmotion.encuestas.dto.EncuestaResponseDTO;
import com.inmotion.encuestas.dto.EstadisticasGeneralesDTO;
import com.inmotion.encuestas.model.Marca;
import com.inmotion.encuestas.service.EncuestaService;
import com.inmotion.encuestas.service.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/encuestas")
@RequiredArgsConstructor
public class EncuestaController {
    
    private final EncuestaService encuestaService;
    private final MarcaService marcaService;
    
    @PostMapping("/crear")
    public ResponseEntity<EncuestaResponseDTO> crearEncuesta(@Valid @RequestBody EncuestaRequestDTO request) {
        return ResponseEntity.ok(encuestaService.crearEncuesta(request));
    }
    
    @PostMapping("/responder/{token}")
    public ResponseEntity<EncuestaResponseDTO> responderEncuesta(
            @PathVariable String token,
            @Valid @RequestBody EncuestaRequestDTO request) {
        return ResponseEntity.ok(encuestaService.responderEncuesta(token, request));
    }
    
    @GetMapping("/listar")
    public ResponseEntity<List<EncuestaResponseDTO>> listarEncuestas() {
        return ResponseEntity.ok(encuestaService.listarEncuestas());
    }

    @GetMapping("/listar/qr")
    public ResponseEntity<List<EncuestaResponseDTO>> listarEncuestasQR() {
        return ResponseEntity.ok(encuestaService.listarEncuestasPorTipo("QR"));
    }
    
    @GetMapping("/token/{token}")
    public ResponseEntity<EncuestaResponseDTO> obtenerEncuestaPorToken(@PathVariable String token) {
        return ResponseEntity.ok(encuestaService.obtenerEncuestaPorToken(token));
    }
    
    @GetMapping("/estadisticas/generales")
    public ResponseEntity<EstadisticasGeneralesDTO> obtenerEstadisticasGenerales() {
        return ResponseEntity.ok(encuestaService.obtenerEstadisticasGenerales());
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEncuesta(@PathVariable Long id) {
        encuestaService.eliminarEncuesta(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/marcas")
    public ResponseEntity<List<Marca>> obtenerMarcas() {
        return ResponseEntity.ok(marcaService.obtenerTodas());
    }
}