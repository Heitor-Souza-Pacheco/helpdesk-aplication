package com.example.demo.DTO;

public class IARespostaDTO {

    private String resposta;
    private boolean bloqueado;
    private String motivo;

    public IARespostaDTO() {
    }

    public IARespostaDTO(String resposta, boolean bloqueado, String motivo) {
        this.resposta = resposta;
        this.bloqueado = bloqueado;
        this.motivo = motivo;
    }

    public static IARespostaDTO ok(String resposta) {
        return new IARespostaDTO(resposta, false, null);
    }

    public static IARespostaDTO bloqueada(String motivo) {
        String mensagem = "Não posso responder a esse pedido. " +
                "Reformule sua pergunta mantendo um tema seguro e relacionado a dúvidas legítimas.";
        return new IARespostaDTO(mensagem, true, motivo);
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
