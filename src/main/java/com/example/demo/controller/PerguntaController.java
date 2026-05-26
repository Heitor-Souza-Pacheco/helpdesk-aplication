package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.PerguntaDTO;
import com.example.demo.services.PerguntaService;
import com.example.demo.services.UserDetailImpl;

@RestController
@RequestMapping(value = "/pergunta")
@CrossOrigin
public class PerguntaController {

    @Autowired
    private PerguntaService perguntaService;

    @GetMapping
    public List<PerguntaDTO> listarTodos() {
        return perguntaService.listarTodos();
    }

    @GetMapping("/minhas")
    public List<PerguntaDTO> listarMinhas(Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        return perguntaService.listarPorUsuario(userDetail.getId());
    }

    @GetMapping("/categoria/{categoria}")
    public List<PerguntaDTO> listarPorCategoria(@PathVariable("categoria") String categoria) {
        return perguntaService.listarPorCategoria(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody PerguntaDTO perguntaDTO, Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        perguntaDTO.setUsuarioId(userDetail.getId());
        perguntaService.criar(perguntaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public PerguntaDTO altera(@RequestBody PerguntaDTO perguntaDTO, Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        perguntaDTO.setUsuarioId(userDetail.getId());
        return perguntaService.editar(perguntaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        perguntaService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
