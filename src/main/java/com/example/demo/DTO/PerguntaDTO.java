package com.example.demo.DTO;

import com.example.demo.entities.Pergunta;

public class PerguntaDTO {

    private Long id;
    private String tituloPergunta;
    private String corpoPergunta;
    private String categoriaPergunta;
    private Long usuarioId;

    public PerguntaDTO(Pergunta pergunta) {
        this.id = pergunta.getId();
        this.tituloPergunta = pergunta.getTituloPergunta();
        this.corpoPergunta = pergunta.getCorpoPergunta();
        this.categoriaPergunta = pergunta.getCategoriaPergunta();
        if (pergunta.getUsuario() != null) {
            this.usuarioId = pergunta.getUsuario().getId();
        }
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

    public String getCategoriaPergunta() {
        return categoriaPergunta;
    }

    public void setCategoriaPergunta(String categoriaPergunta) {
        this.categoriaPergunta = categoriaPergunta;
    }
}