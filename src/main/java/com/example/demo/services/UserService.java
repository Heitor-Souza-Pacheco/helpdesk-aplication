package com.example.demo.services;

import com.example.demo.DTO.UserDTO;
import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    public List<UserDTO> listarTodos(){
        List<User> usuario = usuarioRepository.findAll();
        return usuario.stream().map(UserDTO::new).toList();
    }

    public void inserir(UserDTO userDTO){
        User usuario = new User(userDTO);
        usuarioRepository.save(usuario);
    }

    public UserDTO alterar(UserDTO userDTO){
        User usuario = new User(userDTO);
        return new  UserDTO(usuarioRepository.save(usuario));
    }

    public void excluir(Long id){
        User usuario = usuarioRepository.findById(id).get();
        usuarioRepository.delete(usuario);
    }

    public UserDTO acharPorId(Long id){
        return new UserDTO(usuarioRepository.findById(id).get());
    }


}