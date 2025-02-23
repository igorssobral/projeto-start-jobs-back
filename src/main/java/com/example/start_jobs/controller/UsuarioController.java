package com.example.start_jobs.controller;

import com.example.start_jobs.dto.ForgotPasswordRequest;
import com.example.start_jobs.dto.ResetPasswordRequest;
import com.example.start_jobs.entity.Usuario;
import com.example.start_jobs.service.PasswordResetService;
import com.example.start_jobs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private  PasswordResetService passwordResetService;


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
        try {
            String token = usuarioService.autenticarUsuario(email, senha);
            return ResponseEntity.ok(token);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            System.out.println(request);
            passwordResetService.sendPasswordResetEmail(request.getEmail());
            return ResponseEntity.ok("E-mail de recuperação enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao enviar o e-mail de recuperação: " + e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
            return ResponseEntity.ok("Senha redefinida com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao redefinir a senha: " + e.getMessage());
        }
    }
}
