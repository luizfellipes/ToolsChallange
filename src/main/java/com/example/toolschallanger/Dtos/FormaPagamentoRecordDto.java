package com.example.toolschallanger.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormaPagamentoRecordDto(@NotBlank String tipo, @NotNull Integer parcelas) {
}
