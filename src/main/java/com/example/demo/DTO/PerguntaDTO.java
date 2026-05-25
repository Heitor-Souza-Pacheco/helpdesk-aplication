package com.example.demo.DTO;

import com.example.demo.entities.Pergunta;
import org.springframework.beans.BeanUtils;

public class PerguntaDTO {

    private Long id;
    private String tituloPergunta;
    private String corpoPergunta;
    private Long usuarioId;

    public PerguntaDTO(Pergunta pergunta) {
        BeanUtils.copyProperties(pergunta, this);
    }
    public PerguntaDTO(){
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}