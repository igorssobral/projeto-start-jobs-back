package com.example.start_jobs.controller;

import com.example.start_jobs.entity.Candidatura;
import com.example.start_jobs.service.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {
    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping
    public ResponseEntity<Candidatura> criarCandidatura(@RequestBody Candidatura candidatura) {
        return ResponseEntity.ok(candidaturaService.criarCandidatura(candidatura));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Candidatura>> listarPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(candidaturaService.listarCandidaturasPorUsuario(idUsuario));
    }
}
