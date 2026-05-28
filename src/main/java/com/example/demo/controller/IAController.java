package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.IADTO;
import com.example.demo.DTO.IARespostaDTO;
import com.example.demo.services.IAService;

@RestController
@RequestMapping(value = "/ia")
@CrossOrigin
public class IAController {

    @Autowired
    private IAService iaService;

    @PostMapping("/perguntar")
    public ResponseEntity<IARespostaDTO> perguntar(@RequestBody IADTO entrada) {
        IARespostaDTO resposta = iaService.responder(entrada);
        return ResponseEntity.ok(resposta);
    }
}
