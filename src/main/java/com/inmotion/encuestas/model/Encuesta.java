package com.inmotion.encuestas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "encuestas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Encuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String token;
    
    @Column(nullable = false)
    private String tipo = "SERVICIO";
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEncuesta estado = EstadoEncuesta.ENVIADA;
    
    @Column(nullable = false)
    private LocalDateTime fechaEnvio;
    
    private LocalDateTime fechaExpiracion;
    private LocalDateTime fechaRespuesta;
    
    @Column(nullable = false)
    private String clienteNombre;
    
    private String clienteEmail;
    private String clienteTelefono;
    private String asesor;
    private String serie;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;
    
    @OneToMany(mappedBy = "encuesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respuesta> respuestas = new ArrayList<>();
    
    public enum EstadoEncuesta {
        ENVIADA, RESPONDIDA, EXPIRADA
    }
}