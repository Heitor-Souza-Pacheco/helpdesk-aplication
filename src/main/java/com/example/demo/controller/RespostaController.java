package com.example.demo.controller;

import com.example.demo.DTO.PerguntaDTO;
import com.example.demo.DTO.RespostaDTO;
import com.example.demo.services.RespostaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/resposta")
@CrossOrigin
public class RespostaController {

    @Autowired
    private RespostaService respostaService;

    @GetMapping
    public List<RespostaDTO> listarTodos() {
        return respostaService.listarTodos();
    }

    @GetMapping(value = "/pergunta/{id}")
    public List<RespostaDTO> listarPorPergunta() {
        return respostaService.listarPorPergunta(1L);
    }

    @GetMapping(value = "/usuario/{id}")
    public List<RespostaDTO> listarPorUsuario() {
        return respostaService.listarPorPergunta(1L);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody RespostaDTO respostaDTO) {
        respostaService.criar(respostaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public RespostaDTO altera(@RequestBody RespostaDTO respostaDTO) {
        return respostaService.editar(respostaDTO);
    }
}
