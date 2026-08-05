package com.inmotion.encuestas.service;

import com.inmotion.encuestas.model.Marca;
import com.inmotion.encuestas.repository.MarcaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {
    
    private final MarcaRepository marcaRepository;
    
    @PostConstruct
    public void initMarcas() {
        if (marcaRepository.count() == 0) {
            List<Marca> marcas = List.of(
                    new Marca(null, "Toyota Pachuca"),
                    new Marca(null, "Carsline Pachuca"),
                    new Marca(null, "Carsline Queretaro"),
                    new Marca(null, "GWM Queretaro"),
                    new Marca(null, "Subaru"),
                    new Marca(null, "ComproCars Queretaro"),
                    new Marca(null, "Compro Pachuca")
            );
            marcaRepository.saveAll(marcas);
        }
    }
    
    public List<Marca> obtenerTodas() {
        return marcaRepository.findAll();
    }
}