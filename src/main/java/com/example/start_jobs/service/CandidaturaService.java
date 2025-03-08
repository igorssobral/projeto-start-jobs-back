package com.example.start_jobs.service;

import com.example.start_jobs.dto.CandidaturaDTO;
import com.example.start_jobs.dto.StatusCandidaturaDTO;
import com.example.start_jobs.entity.Candidatura;
import com.example.start_jobs.entity.StatusCandidatura;
import com.example.start_jobs.entity.Usuario;
import com.example.start_jobs.entity.Vaga;
import com.example.start_jobs.exceptions.StatusAlreadyExistsException;
import com.example.start_jobs.repository.CandidaturaRepository;
import com.example.start_jobs.repository.StatusCandidaturaRepository;
import com.example.start_jobs.repository.UsuarioRepository;
import com.example.start_jobs.repository.VagaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidaturaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private StatusCandidaturaRepository statusCandidaturaRepository;

    @Autowired
    private VagaRepository vagaRepository;
    private Candidatura candidaturaSalva;

    @Transactional
    public Candidatura criarCandidatura(CandidaturaDTO candidaturaDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(candidaturaDTO.getIdUsuario());

        if (usuarioOptional.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        Optional<Candidatura> candidaturaExistente = candidaturaRepository
                .findByUsuarioIdUsuarioAndAndVaga_Url(usuario.getIdUsuario(), candidaturaDTO.getVaga().getUrl());

        if (candidaturaExistente.isPresent()) {
            throw new IllegalArgumentException("Você já se candidatou nessa vaga!");
        }

        Vaga vaga = vagaRepository.findVagaByUrl(candidaturaDTO.getVaga().getUrl())
                .orElseGet(() -> {
                    Vaga novaVaga = new Vaga();
                    novaVaga.setTitulo(candidaturaDTO.getVaga().getTitulo());
                    novaVaga.setDescricao(candidaturaDTO.getVaga().getDescricao());
                    novaVaga.setEmpresa(candidaturaDTO.getVaga().getEmpresa());
                    novaVaga.setLocalizacao(candidaturaDTO.getVaga().getLocalizacao());
                    novaVaga.setSenioridade(candidaturaDTO.getVaga().getSenioridade());
                    novaVaga.setModeloTrabalho(candidaturaDTO.getVaga().getModeloTrabalho());
                    novaVaga.setUrl(candidaturaDTO.getVaga().getUrl());
                    novaVaga.setDataCriacao(candidaturaDTO.getVaga().getDataCriacao());
                    return vagaRepository.save(novaVaga);
                });

        Candidatura candidatura = new Candidatura();
        candidatura.setUsuario(usuario);
        candidatura.setVaga(vaga);

        Candidatura candidaturaSalva = candidaturaRepository.save(candidatura);

        List<StatusCandidatura> statusCandidaturasList = new ArrayList<>();
        for (StatusCandidaturaDTO statusDTO : candidaturaDTO.getStatusCandidatura()) {
            StatusCandidatura statusCandidatura = new StatusCandidatura();
            statusCandidatura.setLabel(statusDTO.getLabel());
            statusCandidatura.setApproved(statusDTO.isApproved());
            statusCandidatura.setRejected(statusDTO.isRejected());
            statusCandidatura.setDataStatus(LocalDateTime.now());
            statusCandidatura.setCandidatura(candidaturaSalva);

            statusCandidaturasList.add(statusCandidatura);
        }

        statusCandidaturaRepository.saveAll(statusCandidaturasList);

        return candidaturaSalva;
    }


    @Transactional
    public List<CandidaturaDTO> listarCandidaturas() {
        List<Candidatura> candidatura = candidaturaRepository.findAll();
        return candidatura.stream()
                .map(CandidaturaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<Candidatura> buscarCandidaturaPorId(Long id) {
        return candidaturaRepository.findById(id);
    }

    @Transactional
    public List<CandidaturaDTO> listarCandidaturasPorUsuario(Long idUsuario) {
        List<Candidatura> candidaturas = candidaturaRepository.findByUsuarioIdUsuario(idUsuario);


        return candidaturas.stream()
                .peek(c -> c.getStatusCandidatura().sort(Comparator.comparing(StatusCandidatura::getDataStatus)))
                .map(CandidaturaDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Candidatura atualizarCandidatura(Long id, Candidatura candidaturaAtualizada) {
        Candidatura candidatura = candidaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada"));

        candidatura.setUsuario(candidaturaAtualizada.getUsuario());
        candidatura.setVaga(candidaturaAtualizada.getVaga());
        return candidaturaRepository.save(candidatura);
    }

    @Transactional
    public CandidaturaDTO atualizarStatus(Long id, CandidaturaDTO candidaturaDTO) {
        Optional<Candidatura> candidaturaOptional = candidaturaRepository.findById(id);

        if (candidaturaOptional.isPresent()) {
            Candidatura candidatura = candidaturaOptional.get();

            Map<Integer, StatusCandidatura> statusMap = candidatura.getStatusCandidatura().stream()
                    .collect(Collectors.toMap(StatusCandidatura::getIdStatus, status -> status));

            for (StatusCandidaturaDTO statusDTO : candidaturaDTO.getStatusCandidatura()) {
                StatusCandidatura status = statusMap.get(statusDTO.getId());

                if (status != null) {
                    status.setApproved(statusDTO.isApproved());
                    status.setRejected(statusDTO.isRejected());
                    if (status.getDataStatus() == null) {
                        status.setDataStatus(LocalDateTime.now());
                    }
                }
            }

            // Ordenando os status por data
            candidatura.setStatusCandidatura(candidatura.getStatusCandidatura().stream()
                    .sorted(Comparator.comparing(StatusCandidatura::getDataStatus))
                    .collect(Collectors.toList()));

            candidaturaRepository.save(candidatura);

            return new CandidaturaDTO(candidatura);
        } else {
            throw new RuntimeException("Candidatura não encontrada");
        }
    }



    @Transactional
    public CandidaturaDTO adicionarNovosStatus(Long id, List<StatusCandidaturaDTO> statusCandidaturaDTOList) {
        Optional<Candidatura> candidaturaOptional = candidaturaRepository.findById(id);

        if (candidaturaOptional.isPresent()) {
            Candidatura candidatura = candidaturaOptional.get();

            // Conjunto para verificar a duplicação de status de forma eficiente (utilizando o equals)
            Set<StatusCandidatura> existingStatuses = new HashSet<>(candidatura.getStatusCandidatura());

            List<StatusCandidatura> newStatuses = statusCandidaturaDTOList.stream()
                    .map(statusDTO -> {
                        StatusCandidatura status = new StatusCandidatura(statusDTO, candidatura);

                        if (status.getDataStatus() == null) {
                            status.setDataStatus(LocalDateTime.now());
                        }

                        status.setCandidatura(candidatura);

                        // Verifica se o status já existe
                        if (existingStatuses.contains(status)) {
                            throw new StatusAlreadyExistsException("O status já foi adicionado para esta candidatura.");
                        }

                        return status;
                    })
                    .collect(Collectors.toList());

            // Adiciona os novos status
            candidatura.getStatusCandidatura().addAll(newStatuses);

            // Salva a candidatura com os novos status
            candidaturaRepository.save(candidatura);

            return new CandidaturaDTO(candidatura);
        } else {
            throw new RuntimeException("Candidatura não encontrada");
        }
    }



    public void deletarCandidatura(Long id) {
        candidaturaRepository.deleteById(id);
    }
}
