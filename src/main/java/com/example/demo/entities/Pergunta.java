package com.example.demo.entities;

import java.io.Serializable;

import com.example.demo.DTO.PerguntaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_pergunta")
public class Pergunta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tituloPergunta;

    @Column(nullable = false)
    private String corpoPergunta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    public Pergunta() {
    }

    public Pergunta(PerguntaDTO perguntaDTO) {
        this.id = perguntaDTO.getId();
        this.tituloPergunta = perguntaDTO.getTituloPergunta();
        this.corpoPergunta = perguntaDTO.getCorpoPergunta();
        if (perguntaDTO.getUsuarioId() != null) {
            User user = new User();
            user.setId(perguntaDTO.getUsuarioId());
            this.usuario = user;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloPergunta() {
        return tituloPergunta;
    }

    public void setTituloPergunta(String tituloPergunta) {
        this.tituloPergunta = tituloPergunta;
    }

    public String getCorpoPergunta() {
        return corpoPergunta;
    }

    public void setCorpoPergunta(String corpoPergunta) {
        this.corpoPergunta = corpoPergunta;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }
}
