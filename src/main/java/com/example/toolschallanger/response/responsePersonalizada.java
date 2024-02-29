package com.example.toolschallanger.response;

import com.example.toolschallanger.models.entities.TransacaoModel;

import java.io.Serializable;
import java.util.List;

public class responsePersonalizada implements Serializable {

    private int statusCode;
    private String mensagem;
    private List<TransacaoModel> transacoes;

    public responsePersonalizada(int statusCode, String mensagem, List<TransacaoModel> transacoes) {
        this.statusCode = statusCode;
        this.mensagem = mensagem;
        this.transacoes = transacoes;
    }


}
