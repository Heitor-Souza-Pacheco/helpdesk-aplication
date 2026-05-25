package com.example.demo.controller;

import com.example.demo.DTO.PerguntaDTO;
import com.example.demo.services.PerguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pergunta")
@CrossOrigin
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public List<PerguntaDTO> listarTodos(){
        return perguntaService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody PerguntaDTO perguntaDTO){
        perguntaService.criar(perguntaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public PerguntaDTO altera(@RequestBody PerguntaDTO perguntaDTO){
        return perguntaService.editar(perguntaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        perguntaService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
