package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.RespostaDTO;
import com.example.demo.entities.Resposta;
import com.example.demo.repository.RespostaRepository;

@Service
public class RespostaService {

    @Autowired
    private RespostaRepository respostaRepository;

    public List<RespostaDTO> listarTodos() {
        return respostaRepository.findAll().stream().map(RespostaDTO::new).toList();
    }

    public List<RespostaDTO> listarPorPergunta(Long id) {
        return respostaRepository.findByPerguntaId(id).stream().map(RespostaDTO::new).toList();
    }

    public List<RespostaDTO> listarPorUsuario(Long id) {
        return respostaRepository.findByUsuarioId(id).stream().map(RespostaDTO::new).toList();
    }

    public void criar(RespostaDTO respostaDTO){
        respostaRepository.save(new Resposta(respostaDTO));
    }

    public RespostaDTO editar(RespostaDTO respostaDTO){
        return new RespostaDTO(respostaRepository.save(new Resposta(respostaDTO)));
    }

    public void excluir(Long id){
        Resposta resposta = respostaRepository.findById(id).get();
        respostaRepository.delete(resposta);
    }

    public RespostaDTO curtir(Long id) {
        Resposta resposta = respostaRepository.findById(id).get();
        resposta.setCurtidas(resposta.getCurtidas() + 1);
        return new RespostaDTO(respostaRepository.save(resposta));
    }

    public RespostaDTO descurtir(Long id) {
        Resposta resposta = respostaRepository.findById(id).get();
        if (resposta.getCurtidas() > 0) {
            resposta.setCurtidas(resposta.getCurtidas() - 1);
        }
        return new RespostaDTO(respostaRepository.save(resposta));
    }

    public List<RespostaDTO> listarPorCurtidas() {
        return respostaRepository.findAll().stream().sorted((r1, r2) -> Integer.compare(r2.getCurtidas(), r1.getCurtidas())).map(RespostaDTO::new).toList();
    }
}

