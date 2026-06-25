package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.PerguntaDTO;
import com.example.demo.entities.Pergunta;
import com.example.demo.repository.PerguntaRepository;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    public List<PerguntaDTO> listarTodos() {
        return perguntaRepository.findAll().stream().map(PerguntaDTO::new).toList();
    }

    public List<PerguntaDTO> listarPorCategoria(String categoria) {
        return perguntaRepository.findByCategoriaPergunta(categoria).stream().map(PerguntaDTO::new).toList();
    }

    public List<PerguntaDTO> listarPorUsuario(Long id) {
        return perguntaRepository.findByUsuarioId(id).stream().map(PerguntaDTO::new).toList();
    }

    public void criar(PerguntaDTO perguntaDTO){
        perguntaRepository.save(new Pergunta(perguntaDTO));
    }

    public PerguntaDTO editar(PerguntaDTO perguntaDTO, Long usuarioId){
        Pergunta pergunta = perguntaRepository.findById(perguntaDTO.getId())
            .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

        if (!pergunta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Você não tem permissão para editar essa pergunta.");
        }

        return new PerguntaDTO(perguntaRepository.save(new Pergunta(perguntaDTO)));
    }

    public void excluir(Long id, Long usuarioId){
        Pergunta pergunta = perguntaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));
        
        if (!pergunta.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Você não tem permissão para excluir esta pergunta.");
        }

        perguntaRepository.delete(pergunta);
    }
}
