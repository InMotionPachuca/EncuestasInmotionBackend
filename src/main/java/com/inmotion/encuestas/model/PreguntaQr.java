package com.inmotion.encuestas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preguntas_qr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaQr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String texto;

    @Column(nullable = false)
    private String tipo;

    private Integer orden;
    private Boolean obligatoria = true;
}