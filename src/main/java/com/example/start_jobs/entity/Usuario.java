package com.example.start_jobs.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    private String nome;
    private String email;
    private String telefone;
    private String username;
    private String senha;
    private String imagem;

    @OneToMany(mappedBy = "usuario")
    private List<Candidatura> candidaturas;

    @Column(name = "PASSWORD_RESET_TOKEN")
    private String passwordResetToken;

    @Column(name = "PASSWORD_RESET_EXPIRATION")
    private LocalDateTime passwordResetExpiration;
}