package com.example.demo.services;

import com.example.demo.DTO.RespostaDTO;
import com.example.demo.entities.Resposta;
import com.example.demo.repository.RespostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

