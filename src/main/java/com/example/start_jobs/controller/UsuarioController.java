package com.example.start_jobs.controller;

import com.example.start_jobs.entity.Usuario;
import com.example.start_jobs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new IllegalArgumentException("O email é obrigatório.");
        }
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.ok(usuarioCriado);
    }

    // Endpoint para buscar um usuário pelo email
    @GetMapping("/{email}")
    public ResponseEntity<Optional<Usuario>> buscarUsuario(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
        String token = usuarioService.autenticarUsuario(email, senha);
        System.out.println(token);
        return ResponseEntity.ok(token);
    }
}
