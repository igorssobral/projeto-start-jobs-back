package com.example.start_jobs.repository;

import com.example.start_jobs.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByUsuarioIdUsuario(Long idUsuario);

    Optional<Candidatura> findByUsuarioIdUsuarioAndAndVaga_Url(Integer usuarioId, String vagaUrl);

}
