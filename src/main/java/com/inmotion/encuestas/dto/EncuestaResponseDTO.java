package com.inmotion.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaResponseDTO {
    private Long id;
    private String token;
    private String tipo;
    private String estado;
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaExpiracion;
    private LocalDateTime fechaRespuesta;
    private String clienteNombre;
    private String clienteEmail;
    private String clienteTelefono;
    private String asesor;
    private String unidad;
    private String marcaNombre;
    private Long marcaId;
    private List<RespuestaResponseDTO> respuestas;
}