package com.example.toolschallanger.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public record TransacaoRecordDTO(
        @NotNull(message = "O campo 'cartao' não pode ser nulo ou vazio")
        Long cartao,
        @Valid
        DescricaoRecordDTO descricaoDePagamento,
        @Valid
        FormaPagamentoRecordDTO formaDePagamento) {
}
