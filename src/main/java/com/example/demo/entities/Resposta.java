package com.example.demo.entities;

import com.example.demo.DTO.RespostaDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_resposta")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String corpoResposta;

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(nullable = false)
    private int curtidas;

    @ManyToOne
    @JoinColumn(name = "pergunta_id")
    private Pergunta pergunta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    public Resposta(){
    }

    public Resposta(RespostaDTO respostaDTO){
        this.id = respostaDTO.getId();
        this.corpoResposta = respostaDTO.getCorpoResposta();
        this.nomeUsuario = respostaDTO.getNomeUsuario();
        if (respostaDTO.getPerguntaId() != null) {
            this.pergunta = new Pergunta();
            this.pergunta.setId(respostaDTO.getPerguntaId());
        }
        if (respostaDTO.getUsuarioId() != null) {
            this.usuario = new User();
            this.usuario.setId(respostaDTO.getUsuarioId());
        }
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

    public Pergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(Pergunta pergunta) {
        this.pergunta = pergunta;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public int getCurtidas() {
        return curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
}
