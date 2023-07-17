package com.example.toolschallanger.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DescricaoRecordDto(@NotNull Float valor,
                                 @NotNull CharSequence dataHora,
                                 @NotBlank String estabelecimento) {
}
