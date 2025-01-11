package com.example.start_jobs.service;

import com.example.start_jobs.entity.Usuario;
import com.example.start_jobs.repository.UsuarioRepository;
import com.example.start_jobs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Método para criar um novo usuário
    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografa a senha
        return usuarioRepository.save(usuario);
    }

    // Método para buscar um usuário pelo nome de usuário
    public Optional<Usuario> buscarUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Método para autenticar o login e retornar um token JWT
    public String autenticarUsuario(String username, String senha) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se a senha está correta
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        // Gera o token JWT
        return jwtUtil.generateToken(usuario);
    }
}
