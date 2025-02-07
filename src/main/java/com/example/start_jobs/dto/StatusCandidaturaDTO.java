package com.example.start_jobs.dto;

import com.example.start_jobs.entity.StatusCandidatura;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class StatusCandidaturaDTO {
    private Integer id;
    private String label;
    private boolean completed;
    private CandidaturaDTO candidatura;
    private LocalDateTime dataStatus;


    public StatusCandidaturaDTO(StatusCandidatura statusCandidatura) {
        this.id = statusCandidatura.getIdStatus();
        this.label = statusCandidatura.getLabel();
        this.completed = statusCandidatura.isCompleted();
        this.dataStatus = statusCandidatura.getDataStatus();
    }

}
