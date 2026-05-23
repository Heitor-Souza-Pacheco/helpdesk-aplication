package com.example.demo.controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuario")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDTO> listarTodos(){
        return userService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@RequestBody UserDTO userDTO){
        userService.inserir(userDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public UserDTO altera(@RequestBody UserDTO userDTO){
        return userService.alterar(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        userService.excluir(id);
        return ResponseEntity.ok().build();
    }

}