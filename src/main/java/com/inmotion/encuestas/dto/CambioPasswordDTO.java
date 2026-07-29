package com.inmotion.encuestas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CambioPasswordDTO {
	@NotBlank(message = "El Email es requerido" )
	private String email;
	
	@NotBlank(message = "La contraseña es requerida")
	private String passwordActual;
	
	@NotBlank(message = "La nueva contraseña es requerida")
	@Size(min = 8, message = "La nueva contraseña debe tener al menos 8 caracteres")
	private String nuevaPassword;

}
