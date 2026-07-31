package com.inmotion.encuestas.repository;

import com.inmotion.encuestas.model.PreguntaQr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaQrRepository extends JpaRepository<PreguntaQr, Long> {
    List<PreguntaQr> findAllByOrderByOrdenAsc();
}