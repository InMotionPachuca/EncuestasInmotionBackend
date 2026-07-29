package com.inmotion.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasPorMarcaDTO {
    private Long marcaId;
    private String marcaNombre;
    private Long total;
    private Long respondidas;
    private Long enviadas;
    private Long expiradas;
    private double tasaRespuesta;
    private Double promedioExperiencia;
    private Double promedioAsesor;
    private Long recomendacionSi;
    private Long recomendacionNo;
}