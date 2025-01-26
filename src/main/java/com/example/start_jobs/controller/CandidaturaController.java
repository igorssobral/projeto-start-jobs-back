package com.example.start_jobs.controller;

import com.example.start_jobs.entity.Candidatura;
import com.example.start_jobs.service.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping
    public ResponseEntity<Candidatura> criarCandidatura(@RequestBody Candidatura candidatura) {
        return ResponseEntity.ok(candidaturaService.criarCandidatura(candidatura));
    }

    @GetMapping
    public ResponseEntity<List<Candidatura>> listarCandidaturas() {
        return ResponseEntity.ok(candidaturaService.listarCandidaturas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Candidatura>> buscarCandidaturaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(candidaturaService.buscarCandidaturaPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Candidatura>> listarCandidaturasPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(candidaturaService.listarCandidaturasPorUsuario(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidatura> atualizarCandidatura(
            @PathVariable Long id, @RequestBody Candidatura candidaturaAtualizada) {
        return ResponseEntity.ok(candidaturaService.atualizarCandidatura(id, candidaturaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCandidatura(@PathVariable Long id) {
        candidaturaService.deletarCandidatura(id);
        return ResponseEntity.noContent().build();
    }
}
