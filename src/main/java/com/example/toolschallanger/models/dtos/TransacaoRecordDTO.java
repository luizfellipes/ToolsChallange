package com.example.toolschallanger.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record TransacaoRecordDTO(
        @NotNull(message = "O campo 'cartao' não pode ser nulo ou vazio")
        Long cartao,
        @Valid
        @NotNull
        DescricaoRecordDTO descricaoDePagamento,
        @Valid
        @NotNull
        FormaPagamentoRecordDTO formaDePagamento) {
}
