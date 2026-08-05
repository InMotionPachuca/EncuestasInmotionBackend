package com.inmotion.encuestas.controller;

import com.inmotion.encuestas.dto.CambioPasswordDTO;
import com.inmotion.encuestas.model.Usuario;
import com.inmotion.encuestas.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. Listar todos los usuarios autorizados (Tu método actual)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // 2. Registrar o autorizar a un nuevo usuario institucional (Con validación de longitud de contraseña)
    @PostMapping("/crear")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El correo institucional ya está registrado.");
        }

        if (usuario.getPassword() == null || usuario.getPassword().length() < 8) {
            return ResponseEntity.badRequest().body("La contraseña debe tener mínimo 8 caracteres.");
        }

        // Encriptar la contraseña institucional antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER");
        }
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuarioGuardado);
    }

    // 3. NUEVO: Cambiar contraseña de forma segura
    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@Valid @RequestBody CambioPasswordDTO dto) {
        return usuarioRepository.findByEmail(dto.getEmail())
                .map(usuario -> {
                    // Verificar que la contraseña actual ingresada coincida con la de la BD
                    if (!passwordEncoder.matches(dto.getPasswordActual(), usuario.getPassword())) {
                        return ResponseEntity.badRequest().body("La contraseña actual es incorrecta.");
                    }

                    // Encriptar y actualizar la nueva contraseña
                    usuario.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
                    usuarioRepository.save(usuario);

                    return ResponseEntity.ok("Contraseña actualizada exitosamente.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. Activar o desactivar el acceso a un usuario (Tu método actual)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setActivo(activo);
                    usuarioRepository.save(usuario);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Eliminar usuario (Tu método actual)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}