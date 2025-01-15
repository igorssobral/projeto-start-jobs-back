package com.example.start_jobs.controller;

import com.example.start_jobs.entity.Dicas;
import com.example.start_jobs.service.DicasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dicas")
public class DicasController {

    @Autowired
    private DicasService dicasService;

    @PostMapping
    public ResponseEntity<Dicas> criarDica(@RequestBody Dicas dica) {
        return ResponseEntity.ok(dicasService.criarDica(dica));
    }

    @GetMapping
    public ResponseEntity<List<Dicas>> listarDicas() {
        return ResponseEntity.ok(dicasService.listarDicas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dicas> buscarDicaPorId(@PathVariable Long id) {
        return dicasService.buscarDicaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dicas> atualizarDica(@PathVariable Long id, @RequestBody Dicas dicaAtualizada) {
        return ResponseEntity.ok(dicasService.atualizarDica(id, dicaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDica(@PathVariable Long id) {
        dicasService.deletarDica(id);
        return ResponseEntity.noContent().build();
    }
}
