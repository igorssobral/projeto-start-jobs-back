package com.example.start_jobs.controller;

import com.example.start_jobs.entity.Vaga;
import com.example.start_jobs.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {
    @Autowired
    private VagaService vagaService;

    @GetMapping
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaService.listarTodasAsVagas();
        return ResponseEntity.ok(vagas);
    }

    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@RequestBody Vaga vaga) {
        Vaga novaVaga = vagaService.criarVaga(vaga);
        return ResponseEntity.status(201).body(novaVaga);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizarVaga(@PathVariable Long id, @RequestBody Vaga vaga) {
        Vaga vagaAtualizada = vagaService.atualizarVaga(id, vaga);
        return ResponseEntity.ok(vagaAtualizada);
    }
}