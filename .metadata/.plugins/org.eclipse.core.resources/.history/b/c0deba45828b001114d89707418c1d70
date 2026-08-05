package com.inmotion.encuestas.config;

import com.inmotion.encuestas.model.Usuario;
import com.inmotion.encuestas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByEmail("admin@inmotion.com")) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@inmotion.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador");
            admin.setRol("ADMIN");
            admin.setActivo(true);
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario admin creado: admin@inmotion.com / admin123");
        }
    }
}