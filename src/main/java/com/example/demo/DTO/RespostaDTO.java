package com.example.demo.DTO;

import com.example.demo.entities.Pergunta;
import com.example.demo.entities.Resposta;

public class RespostaDTO {

    private Long id;
    private String corpoResposta;
    private String nomeUsuario;
    private int curtidas;
    private Long usuarioId;
    private Long perguntaId;

    public RespostaDTO() {
    }

    public RespostaDTO(Resposta resposta) {
        this.id = resposta.getId();
        this.corpoResposta = resposta.getCorpoResposta();
        this.nomeUsuario = resposta.getNomeUsuario();
        this.curtidas = resposta.getCurtidas();
        this.usuarioId = resposta.getUsuario() != null ? resposta.getUsuario().getId() : null;
        this.perguntaId = resposta.getPergunta() != null ? resposta.getPergunta().getId() : null;
    }

    public RespostaDTO(Long id, String corpoResposta, String nomeUsuario, int curtidas, Long usuarioId, Long perguntaId) {
        this.id = id;
        this.corpoResposta = corpoResposta;
        this.nomeUsuario = nomeUsuario;
        this.curtidas = curtidas;
        this.usuarioId = usuarioId;
        this.perguntaId = perguntaId;
    }

    public RespostaDTO(Pergunta pergunta) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorpoResposta() {
        return corpoResposta;
    }

    public void setCorpoResposta(String corpoResposta) {
        this.corpoResposta = corpoResposta;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPerguntaId() {
        return perguntaId;
    }

    public void setPerguntaId(Long perguntaId) {
        this.perguntaId = perguntaId;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
}
