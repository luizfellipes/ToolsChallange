package com.example.toolschallanger.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record TransacaoRecordDTO(@NotBlank @NotNull Long cartao,
                                 @NotBlank DescricaoRecordDTO descricaoRecordDTO,
                                 @NotBlank FormaPagamentoRecordDTO formaPagamentoRecordDTO) {
}
