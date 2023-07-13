package com.example.toolschallanger.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDateTime;


public record TransacaoRecordDto(
        @NotNull Long card,
        @NotNull Float valor,
        @NotNull
        CharSequence dataHora,
        @NotNull String estabelecimento,
        @NotNull String tipo,
        @NotNull int parcelas) {
}