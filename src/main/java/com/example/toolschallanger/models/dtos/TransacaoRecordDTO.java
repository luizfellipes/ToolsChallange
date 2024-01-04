package com.example.toolschallanger.models.dtos;

import jakarta.validation.constraints.NotNull;


public record TransacaoRecordDTO(@NotNull Long cartao, @NotNull DescricaoRecordDTO descricaoRecordDTO, @NotNull FormaPagamentoRecordDTO formaPagamentoRecordDTO) {


//    @Override
//    public TransacaoModel transacaoModel() {
//        if (transacaoModel.getDescricaoModel() != null || transacaoModel.getFormaPagamentoModel() != null) {
//            return transacaoModel;
//        } else {
//            throw new RuntimeException("Transacao Vazia !");
//        }
//    }
}
