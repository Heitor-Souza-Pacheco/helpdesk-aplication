package com.example.demo.DTO;

import com.example.demo.entities.User;
import org.springframework.beans.BeanUtils;

public class UserDTO {

    private Long id;

    private String email;

    private String password;

    public UserDTO(User usuario) {

        BeanUtils.copyProperties(usuario, this);
    }
    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
