package com.example.start_jobs.repository;

import com.example.start_jobs.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByUsuarioIdUsuario(Long idUsuario);
}
