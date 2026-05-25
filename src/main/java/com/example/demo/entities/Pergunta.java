package com.example.demo.entities;

import com.example.demo.DTO.PerguntaDTO;
import jakarta.persistence.*;

import java.io.Serializable;

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

    public Pergunta(PerguntaDTO perguntaDTO) {
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
