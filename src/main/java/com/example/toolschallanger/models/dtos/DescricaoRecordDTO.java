package com.example.toolschallanger.models.dtos;

import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;

public record DescricaoRecordDTO(@NotBlank Double valor,
                                 @NotBlank LocalDateTime dataHora,
                                 @NotBlank String estabelecimento) {
}
