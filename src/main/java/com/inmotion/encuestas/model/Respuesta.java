package com.inmotion.encuestas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "respuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer preguntaId;
    
    @Column(length = 500)
    private String valorTexto;
    
    private Integer valorNumerico;
    
    private Integer opcionId;
    
    @Column(length = 200)
    private String opcionTexto;
    
    @Column(length = 500)
    private String preguntaTexto;
    
    @ManyToOne
    @JoinColumn(name = "encuesta_id", nullable = false)
    private Encuesta encuesta;
}