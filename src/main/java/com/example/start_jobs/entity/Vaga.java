package com.example.start_jobs.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Vaga")
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVaga;

    private String titulo;
    private String descricao;
    private String empresa;
    private String localizacao;

    @Column(name = "data_criacao", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dataCriacao;

    private String imagem;
}
