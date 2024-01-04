package com.example.toolschallanger.models.dtos;

import com.example.toolschallanger.models.entities.TransacaoModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TransacaoRecordDto(@NotNull
                                 @JsonProperty
                                 TransacaoModel transacaoModel) {
//    @Override
//    public TransacaoModel transacaoModel() {
//        if (transacaoModel.getDescricaoModel() != null || transacaoModel.getFormaPagamentoModel() != null) {
//            return transacaoModel;
//        } else {
//            throw new RuntimeException("Transacao Vazia !");
//        }
//    }
}
