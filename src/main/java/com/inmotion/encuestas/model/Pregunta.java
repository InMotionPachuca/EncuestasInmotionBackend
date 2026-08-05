package com.inmotion.encuestas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preguntas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String texto;
    
    @Column(nullable = false)
    private String tipo; // TEXTO, NUMERICO, OPCION, RANGO, SI_NO, ESCALA
    
    private Boolean obligatoria = true;
    
    private Integer orden;
    
    @Column(length = 500)
    private String opciones; // JSON con opciones
}