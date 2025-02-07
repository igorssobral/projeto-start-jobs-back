package com.example.start_jobs.dto;

import com.example.start_jobs.entity.Candidatura;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CandidaturaDTO {

    private Integer id;
    private Integer idUsuario;
    private VagaDTO vaga;
    private List<StatusCandidaturaDTO> statusCandidatura;
    private LocalDateTime dataCandidatura;

    public CandidaturaDTO(Candidatura candidatura) {
        this.id = candidatura.getIdCandidatura();
        this.idUsuario = candidatura.getUsuario().getIdUsuario();
        this.vaga = new VagaDTO(candidatura.getVaga());
        this.dataCandidatura = candidatura.getDataCandidatura();
        this.statusCandidatura = candidatura.getStatusCandidatura().stream()
                .map(StatusCandidaturaDTO::new)
                .collect(Collectors.toList());    }
}
