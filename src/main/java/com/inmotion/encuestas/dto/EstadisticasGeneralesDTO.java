package com.inmotion.encuestas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticasGeneralesDTO {
    private Long total;
    private Long enviadas;
    private Long respondidas;
    private Long expiradas;
    private double tasaRespuesta;
    private PromediosDTO promedios;
    private List<EstadisticasPorMarcaDTO> porMarca;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PromediosDTO {
        private double experiencia;
        private double asesor;
        private double recomendacion;
    }
}