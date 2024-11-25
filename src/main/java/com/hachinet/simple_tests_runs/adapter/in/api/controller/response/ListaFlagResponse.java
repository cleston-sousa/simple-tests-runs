package com.hachinet.simple_tests_runs.adapter.in.api.controller.response;

import java.util.ArrayList;
import java.util.List;

public class ListaFlagResponse {

    private String mensagem;

    private List<Object> objetos = new ArrayList<>();


    public ListaFlagResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


    public List<Object> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Object> objetos) {
        this.objetos = objetos;
    }
}
