package com.example.toolschallanger.models.dtos;

import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.validation.constraints.NotBlank;


public record FormaPagamentoRecordDTO(@NotBlank FormaPagamento tipo,
                                      @NotBlank Integer parcelas) {
}
