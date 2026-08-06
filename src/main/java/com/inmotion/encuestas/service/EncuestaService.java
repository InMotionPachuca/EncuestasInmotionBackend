package com.inmotion.encuestas.service;

import com.inmotion.encuestas.dto.*;
import com.inmotion.encuestas.model.*;
import com.inmotion.encuestas.repository.EncuestaRepository;
import com.inmotion.encuestas.repository.MarcaRepository;
import com.inmotion.encuestas.repository.RespuestaRepository;
import com.inmotion.encuestas.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EncuestaService {
    
    private final EncuestaRepository encuestaRepository;
    private final RespuestaRepository respuestaRepository;
    private final MarcaRepository marcaRepository;
    private final TokenGenerator tokenGenerator;
    
    @Transactional
    public EncuestaResponseDTO crearEncuesta(EncuestaRequestDTO request) {
        Marca marca = marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        
        Encuesta encuesta = new Encuesta();
        encuesta.setToken(tokenGenerator.generarToken());
        
        String tipo = request.getTipo() != null ? request.getTipo() : "SERVICIO";
        encuesta.setTipo(tipo);
        
        // Si la encuesta es de tipo QR, nace como RESPONDIDA inmediatamente
        if ("QR".equalsIgnoreCase(tipo)) {
            encuesta.setEstado(Encuesta.EstadoEncuesta.RESPONDIDA);
            encuesta.setFechaEnvio(LocalDateTime.now());
            encuesta.setFechaRespuesta(LocalDateTime.now());
        } else {
            encuesta.setEstado(Encuesta.EstadoEncuesta.ENVIADA);
            encuesta.setFechaEnvio(LocalDateTime.now());
            encuesta.setFechaExpiracion(LocalDateTime.now().plusDays(7));
        }

        encuesta.setClienteNombre(request.getNombre() + " " + request.getApellido());
        encuesta.setClienteEmail(request.getEmail());
        encuesta.setClienteTelefono(request.getTelefono());
        encuesta.setAsesor(request.getAsesor());
        encuesta.setSerie(request.getSerie());
        encuesta.setSerie(request.getUnidad());
        encuesta.setMarca(marca);

        // --- CORRECCIÓN CRÍTICA: Mapear y guardar respuestas cuando vienen en el request (Caso QR) ---
        if (request.getRespuestas() != null && !request.getRespuestas().isEmpty()) {
            for (RespuestaRequestDTO r : request.getRespuestas()) {
                Respuesta respuesta = new Respuesta();
                respuesta.setPreguntaId(r.getPreguntaId());
                respuesta.setValorTexto(r.getValorTexto());
                respuesta.setValorNumerico(r.getValorNumerico());
                respuesta.setOpcionId(r.getOpcionId());
                
                if (r.getOpcionId() != null) {
                    respuesta.setOpcionTexto(r.getOpcionId() == 1 ? "SI" : "NO");
                }
                
                respuesta.setPreguntaTexto(getPreguntaTexto(r.getPreguntaId()));
                respuesta.setEncuesta(encuesta);
                encuesta.getRespuestas().add(respuesta);
            }
        }
        
        Encuesta saved = encuestaRepository.save(encuesta);
        return mapToResponseDTO(saved);
    }
    
    @Transactional
    public EncuestaResponseDTO responderEncuesta(String token, EncuestaRequestDTO request) {
        Encuesta encuesta = encuestaRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
        
        if (encuesta.getEstado() == Encuesta.EstadoEncuesta.RESPONDIDA) {
            throw new RuntimeException("Esta encuesta ya fue respondida");
        }
        
        if (encuesta.getEstado() == Encuesta.EstadoEncuesta.EXPIRADA) {
            throw new RuntimeException("Esta encuesta ha expirado");
        }
        
        // Limpiar respuestas anteriores
        encuesta.getRespuestas().clear();
        
        // Guardar nuevas respuestas
        for (RespuestaRequestDTO r : request.getRespuestas()) {
            Respuesta respuesta = new Respuesta();
            respuesta.setPreguntaId(r.getPreguntaId());
            respuesta.setValorTexto(r.getValorTexto());
            respuesta.setValorNumerico(r.getValorNumerico());
            respuesta.setOpcionId(r.getOpcionId());
            
            if (r.getOpcionId() != null) {
                respuesta.setOpcionTexto(r.getOpcionId() == 1 ? "SI" : "NO");
            }
            
            respuesta.setPreguntaTexto(getPreguntaTexto(r.getPreguntaId()));
            respuesta.setEncuesta(encuesta);
            encuesta.getRespuestas().add(respuesta);
        }
        
        encuesta.setEstado(Encuesta.EstadoEncuesta.RESPONDIDA);
        encuesta.setFechaRespuesta(LocalDateTime.now());
        
        Encuesta saved = encuestaRepository.save(encuesta);
        return mapToResponseDTO(saved);
    }
    
    public List<EncuestaResponseDTO> listarEncuestas() {
        return encuestaRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EncuestaResponseDTO> listarEncuestasPorTipo(String tipo) {
        return encuestaRepository.findByTipoOrderByIdDesc(tipo).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public EncuestaResponseDTO obtenerEncuestaPorToken(String token) {
        Encuesta encuesta = encuestaRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));
        return mapToResponseDTO(encuesta);
    }
    
    public EstadisticasGeneralesDTO obtenerEstadisticasGenerales() {
        EstadisticasGeneralesDTO stats = new EstadisticasGeneralesDTO();
        
        stats.setTotal(encuestaRepository.countTotal());
        stats.setEnviadas(encuestaRepository.countEnviadas());
        stats.setRespondidas(encuestaRepository.countRespondidas());
        stats.setExpiradas(encuestaRepository.countExpiradas());
        
        double tasa = stats.getTotal() > 0 ? 
                (double) stats.getRespondidas() / stats.getTotal() * 100 : 0;
        stats.setTasaRespuesta(Math.round(tasa * 10) / 10.0);
        
        // Promedios
        EstadisticasGeneralesDTO.PromediosDTO promedios = new EstadisticasGeneralesDTO.PromediosDTO();
        promedios.setExperiencia(obtenerPromedio(2));
        promedios.setAsesor(obtenerPromedio(3));
        promedios.setRecomendacion(obtenerPromedioRecomendacion());
        stats.setPromedios(promedios);
        
        // Por marca
        List<EstadisticasPorMarcaDTO> porMarca = new ArrayList<>();
        List<Object[]> resultados = encuestaRepository.countByMarca();
        
        for (Object[] row : resultados) {
            EstadisticasPorMarcaDTO dto = new EstadisticasPorMarcaDTO();
            dto.setMarcaId((Long) row[0]);
            dto.setMarcaNombre((String) row[1]);
            dto.setTotal((Long) row[2]);
            dto.setRespondidas((Long) row[3]);
            dto.setEnviadas((Long) row[4]);
            
            double tasaMarca = dto.getTotal() > 0 ?
                    (double) dto.getRespondidas() / dto.getTotal() * 100 : 0;
            dto.setTasaRespuesta(Math.round(tasaMarca * 10) / 10.0);
            
            porMarca.add(dto);
        }
        
        stats.setPorMarca(porMarca);
        return stats;
    }
    
    @Transactional
    public void eliminarEncuesta(Long id) {
        if (!encuestaRepository.existsById(id)) {
            throw new RuntimeException("Encuesta no encontrada");
        }
        encuestaRepository.deleteById(id);
    }
    
    private double obtenerPromedio(Integer preguntaId) {
        Double promedio = respuestaRepository.promedioByPreguntaId(preguntaId);
        return promedio != null ? Math.round(promedio * 10) / 10.0 : 0.0;
    }
    
    private double obtenerPromedioRecomendacion() {
        List<Object[]> resultados = respuestaRepository.countOpcionesByPreguntaId(5);
        long si = 0, no = 0;
        for (Object[] row : resultados) {
            if (row[0] != null) {
                if ((Integer) row[0] == 1) {
                    si = (Long) row[1];
                } else if ((Integer) row[0] == 2) {
                    no = (Long) row[1];
                }
            }
        }
        long total = si + no;
        return total > 0 ? Math.round((double) si / total * 100 * 10) / 10.0 : 0;
    }
    
    private String getPreguntaTexto(Integer preguntaId) {
        switch (preguntaId) {
            case 1: return "Nombre Completo del Cliente";
            case 2: return "Experiencia general con la agencia";
            case 3: return "Atención amable y profesional";
            case 4: return "Tiempo de atención adecuado";
            case 5: return "Recomendación a familiares y amigos";
            case 6: return "Sugerencias de mejora";
            default: return "Pregunta " + preguntaId;
        }
    }
    
    private EncuestaResponseDTO mapToResponseDTO(Encuesta encuesta) {
        EncuestaResponseDTO dto = new EncuestaResponseDTO();
        dto.setId(encuesta.getId());
        dto.setToken(encuesta.getToken());
        dto.setTipo(encuesta.getTipo());
        dto.setEstado(encuesta.getEstado().name());
        dto.setFechaEnvio(encuesta.getFechaEnvio());
        dto.setFechaExpiracion(encuesta.getFechaExpiracion());
        dto.setFechaRespuesta(encuesta.getFechaRespuesta());
        dto.setClienteNombre(encuesta.getClienteNombre());
        dto.setClienteEmail(encuesta.getClienteEmail());
        dto.setClienteTelefono(encuesta.getClienteTelefono());
        dto.setAsesor(encuesta.getAsesor());
        dto.setUnidad(encuesta.getUnidad());
        dto.setMarcaNombre(encuesta.getMarca().getNombre());
        dto.setMarcaId(encuesta.getMarca().getId());
        
        List<RespuestaResponseDTO> respuestas = encuesta.getRespuestas().stream()
                .map(r -> {
                    RespuestaResponseDTO rdto = new RespuestaResponseDTO();
                    rdto.setId(r.getId());
                    rdto.setPreguntaId(r.getPreguntaId());
                    rdto.setPreguntaTexto(r.getPreguntaTexto());
                    rdto.setValorTexto(r.getValorTexto());
                    rdto.setValorNumerico(r.getValorNumerico());
                    rdto.setOpcionId(r.getOpcionId());
                    rdto.setOpcionTexto(r.getOpcionTexto());
                    return rdto;
                })
                .collect(Collectors.toList());
        dto.setRespuestas(respuestas);
        
        return dto;
    }
}