package com.example.start_jobs.service;

import com.example.start_jobs.entity.Candidatura;
import com.example.start_jobs.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidaturaService {
    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public Candidatura criarCandidatura(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }

    public List<Candidatura> listarCandidaturasPorUsuario(Long idUsuario) {
        return candidaturaRepository.findByUsuarioIdUsuario(idUsuario);
    }
}
