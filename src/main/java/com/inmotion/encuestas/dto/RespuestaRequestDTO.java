package com.inmotion.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaRequestDTO {
    private Integer preguntaId;
    private String valorTexto;
    private Integer valorNumerico;
    private Integer opcionId;
}