package com.example.start_jobs.service;

import com.example.start_jobs.entity.Dicas;
import com.example.start_jobs.repository.DicasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DicasService {

    @Autowired
    private DicasRepository dicasRepository;

    public Dicas criarDica(Dicas dica) {
        dica.setDataPublicacao(LocalDateTime.now()); // Define a data de publicação como o horário atual
        return dicasRepository.save(dica);
    }

    public List<Dicas> listarDicas() {
        return dicasRepository.findAll();
    }

    public Optional<Dicas> buscarDicaPorId(Long id) {
        return dicasRepository.findById(id);
    }

    public Dicas atualizarDica(Long id, Dicas dicaAtualizada) {
        return dicasRepository.findById(id).map(dica -> {
            dica.setTitulo(dicaAtualizada.getTitulo());
            dica.setDescricao(dicaAtualizada.getDescricao());
            dica.setCategoria(dicaAtualizada.getCategoria());
            dica.setImagem(dicaAtualizada.getImagem());
            return dicasRepository.save(dica);
        }).orElseThrow(() -> new RuntimeException("Dica não encontrada!"));
    }

    public void deletarDica(Long id) {
        dicasRepository.deleteById(id);
    }
}
