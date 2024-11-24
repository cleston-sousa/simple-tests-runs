package com.hachinet.simple_tests_runs.adapter.in.api.controller.response;

public class ListaFlagResponse {

    private String mensagem;


    public ListaFlagResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
