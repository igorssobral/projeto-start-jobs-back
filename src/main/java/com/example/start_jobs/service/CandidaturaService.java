package com.example.start_jobs.service;

import com.example.start_jobs.entity.Candidatura;
import com.example.start_jobs.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public Candidatura criarCandidatura(Candidatura candidatura) {
        return candidaturaRepository.save(candidatura);
    }

    public List<Candidatura> listarCandidaturas() {
        return candidaturaRepository.findAll();
    }

    public Optional<Candidatura> buscarCandidaturaPorId(Long id) {
        return candidaturaRepository.findById(id);
    }

    public List<Candidatura> listarCandidaturasPorUsuario(Long idUsuario) {
        return candidaturaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public Candidatura atualizarCandidatura(Long id, Candidatura candidaturaAtualizada) {
        Candidatura candidatura = candidaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        candidatura.setStatus(candidaturaAtualizada.getStatus());
        candidatura.setUsuario(candidaturaAtualizada.getUsuario());
        candidatura.setVaga(candidaturaAtualizada.getVaga());
        return candidaturaRepository.save(candidatura);
    }

    public void deletarCandidatura(Long id) {
        candidaturaRepository.deleteById(id);
    }
}
