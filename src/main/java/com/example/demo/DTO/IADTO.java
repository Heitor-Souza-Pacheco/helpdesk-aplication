package com.example.demo.DTO;

public class IADTO {

    private String pergunta;

    public IADTO() {
    }

    public IADTO(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }
}
