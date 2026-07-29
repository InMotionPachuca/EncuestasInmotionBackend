package com.inmotion.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaResponseDTO {
    private Long id;
    private Integer preguntaId;
    private String preguntaTexto;
    private String valorTexto;
    private Integer valorNumerico;
    private Integer opcionId;
    private String opcionTexto;
}