package com.inmotion.encuestas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health") // Puedes usar la ruta que prefieras, como /api/health
    public ResponseEntity<String> healthCheck() {
        // Aquí podrías, de manera opcional, agregar lógica para verificar
        // que tu base de datos u otras dependencias estén funcionando.
        // Para empezar, con devolver "OK" es suficiente.
        return ResponseEntity.ok("Backend is active");
    }
}