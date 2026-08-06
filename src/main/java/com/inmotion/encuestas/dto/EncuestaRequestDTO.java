package com.inmotion.encuestas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncuestaRequestDTO {
    @NotBlank(message = "El nombre es requerido")
    private String nombre;
    
    @NotBlank(message = "El apellido es requerido")
    private String apellido;
    
    @Email(message = "Email inválido")
    private String email;
    
    private String telefono;
    
    private String asesor;
    
    private String serie;

    private String unidad;
    
    @NotNull(message = "La marca es requerida")
    private Long marcaId;
    
    private String tipo = "SERVICIO";
    
    private List<RespuestaRequestDTO> respuestas;
}