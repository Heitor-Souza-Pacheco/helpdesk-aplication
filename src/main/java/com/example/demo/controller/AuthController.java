package com.example.demo.controller;

import com.example.demo.DTO.AuthenticationDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.services.AuthService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO authDto) {
        var resultado = authService.login(authDto);
        if (resultado == null) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos.");
        }
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        userService.inserir(userDTO);
        return ResponseEntity.ok("Usuário cadastrado com sucesso.");
    }
}
