package com.example.toolschallanger.models.dtos;

import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.validation.constraints.NotNull;

public record FormaPagamentoRecordDTO(@NotNull FormaPagamento tipo, Integer parcelas) {
}
