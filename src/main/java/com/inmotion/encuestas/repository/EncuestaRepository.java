package com.inmotion.encuestas.repository;

import com.inmotion.encuestas.model.Encuesta;
import com.inmotion.encuestas.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long> {
    
    Optional<Encuesta> findByToken(String token);
    
    List<Encuesta> findByEstado(Encuesta.EstadoEncuesta estado);
    
    List<Encuesta> findByMarca(Marca marca);
    
    // Método nuevo para listar según el tipo ('QR' o 'SERVICIO')
    List<Encuesta> findByTipoOrderByIdDesc(String tipo);
    
    @Query("SELECT e FROM Encuesta e WHERE e.estado = 'ENVIADA' AND e.fechaExpiracion < :fecha")
    List<Encuesta> findExpiradas(@Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT COUNT(e) FROM Encuesta e")
    long countTotal();
    
    @Query("SELECT COUNT(e) FROM Encuesta e WHERE e.estado = 'ENVIADA'")
    long countEnviadas();
    
    @Query("SELECT COUNT(e) FROM Encuesta e WHERE e.estado = 'RESPONDIDA'")
    long countRespondidas();
    
    @Query("SELECT COUNT(e) FROM Encuesta e WHERE e.estado = 'EXPIRADA'")
    long countExpiradas();
    
    @Query("SELECT e.marca.id as marcaId, e.marca.nombre as marcaNombre, COUNT(e) as total, " +
           "SUM(CASE WHEN e.estado = 'RESPONDIDA' THEN 1 ELSE 0 END) as respondidas, " +
           "SUM(CASE WHEN e.estado = 'ENVIADA' THEN 1 ELSE 0 END) as enviadas " +
           "FROM Encuesta e GROUP BY e.marca.id, e.marca.nombre")
    List<Object[]> countByMarca();
}