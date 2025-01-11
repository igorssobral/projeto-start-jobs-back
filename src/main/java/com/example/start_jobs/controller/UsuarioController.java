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
        return ResponseEntity.ok(usuarioService.criarUsuario(usuario));
    }

    // Endpoint para buscar um usuário pelo username
    @GetMapping("/{username}")
    public ResponseEntity<Optional<Usuario>> buscarUsuario(@PathVariable String username) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorUsername(username));
    }

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String senha) {
        String token = usuarioService.autenticarUsuario(username, senha);
        return ResponseEntity.ok(token);
    }
}
