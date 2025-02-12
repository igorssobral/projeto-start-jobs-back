package com.example.start_jobs.repository;

import com.example.start_jobs.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

    Optional<Vaga> findVagaByUrl(String url);
}