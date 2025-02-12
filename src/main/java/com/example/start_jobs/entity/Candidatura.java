package com.example.start_jobs.entity;

import com.example.start_jobs.dto.CandidaturaDTO;
import com.example.start_jobs.dto.StatusCandidaturaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "Candidatura")
@AllArgsConstructor
@NoArgsConstructor
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCandidatura;

    @OneToMany(mappedBy = "candidatura", cascade = CascadeType.ALL)
    private List<StatusCandidatura> statusCandidatura;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Vaga vaga;



    @Column(name = "data_candidatura")
    private LocalDateTime dataCandidatura;

    public Candidatura(CandidaturaDTO candidatura) {
        this.idCandidatura = candidatura.getId();
        this.statusCandidatura =  candidatura.getStatusCandidatura().stream()
                .map(StatusCandidatura::new)
                .collect(Collectors.toList());
        this.vaga = new Vaga(candidatura.getVaga());
        this.dataCandidatura = candidatura.getDataCandidatura();
    }

    @PrePersist
    public void prePersist() {
        if (dataCandidatura == null) {
            dataCandidatura = LocalDateTime.now();
        }
    }
}
