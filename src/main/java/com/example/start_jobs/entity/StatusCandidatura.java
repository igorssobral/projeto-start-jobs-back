package com.example.start_jobs.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "StatusCandidatura")
public class StatusCandidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;

    private String descricao;
}
