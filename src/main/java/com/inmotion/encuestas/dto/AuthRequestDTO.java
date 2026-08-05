package com.inmotion.encuestas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @NotBlank(message = "El email es requerido")
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "La contraseña es requerida")
    private String password;
}