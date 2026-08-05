package com.inmotion.encuestas.repository;

import com.inmotion.encuestas.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    
    List<Respuesta> findByEncuestaId(Long encuestaId);
    
    @Query("SELECT AVG(r.valorNumerico) FROM Respuesta r WHERE r.preguntaId = :preguntaId AND r.valorNumerico IS NOT NULL")
    Double promedioByPreguntaId(@Param("preguntaId") Integer preguntaId);
    
    @Query("SELECT r.opcionId, COUNT(r) FROM Respuesta r WHERE r.preguntaId = :preguntaId AND r.opcionId IS NOT NULL GROUP BY r.opcionId")
    List<Object[]> countOpcionesByPreguntaId(@Param("preguntaId") Integer preguntaId);
}