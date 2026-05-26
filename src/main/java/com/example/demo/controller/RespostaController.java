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

import com.example.demo.DTO.RespostaDTO;
import com.example.demo.services.RespostaService;
import com.example.demo.services.UserDetailImpl;

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

    @GetMapping("/minhas")
    public List<RespostaDTO> listarMinhas(Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        return respostaService.listarPorUsuario(userDetail.getId());
    }

    @GetMapping("/pergunta/{id}")
    public List<RespostaDTO> listarPorPergunta(@PathVariable("id") Long id) {
        return respostaService.listarPorPergunta(id);
    }

    @GetMapping("/curtidas")
    public List<RespostaDTO> listarPorCurtidas() {
        return respostaService.listarPorCurtidas();
    }

    @PutMapping("/curtir/{id}")
    public RespostaDTO curtir(@PathVariable("id") Long id) {
        return respostaService.curtir(id);
    }

    @PutMapping("/descurtir/{id}")
    public RespostaDTO descurtir(@PathVariable("id") Long id) {
        return respostaService.descurtir(id);
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody RespostaDTO respostaDTO, Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        respostaDTO.setUsuarioId(userDetail.getId());
        respostaDTO.setNomeUsuario(userDetail.getUsername());
        respostaService.criar(respostaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public RespostaDTO altera(@RequestBody RespostaDTO respostaDTO, Authentication authentication) {
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        respostaDTO.setUsuarioId(userDetail.getId());
        return respostaService.editar(respostaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        respostaService.excluir(id);
        return ResponseEntity.ok().build();
    }
}
