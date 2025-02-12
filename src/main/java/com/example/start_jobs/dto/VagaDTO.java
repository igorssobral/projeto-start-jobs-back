package com.example.start_jobs.dto;

import com.example.start_jobs.entity.Vaga;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class VagaDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private String empresa;
    private String localizacao;
    private String senioridade;
    private String modeloTrabalho;
    private List<CandidaturaDTO> candidaturas;
    private LocalDate dataCriacao;
    private String url;

    public VagaDTO(Vaga vaga) {
        this.id = vaga.getIdVaga();
        this.titulo = vaga.getTitulo();
        this.descricao = vaga.getDescricao();
        this.empresa = vaga.getEmpresa();
        this.localizacao = vaga.getLocalizacao();
        this.senioridade = vaga.getSenioridade();
        this.modeloTrabalho = vaga.getModeloTrabalho();
        this.dataCriacao = vaga.getDataCriacao();
        this.url = vaga.getUrl();
    }
}
