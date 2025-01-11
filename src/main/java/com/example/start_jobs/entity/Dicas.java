package com.example.start_jobs.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Dicas")
public class Dicas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDica;

    private String titulo;
    private String descricao;
    private String categoria;

    @Column(name = "data_publicacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataPublicacao;

    private String imagem;
}