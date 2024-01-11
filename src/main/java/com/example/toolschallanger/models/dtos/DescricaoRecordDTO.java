package com.example.toolschallanger.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DescricaoRecordDTO(
        @NotNull(message = "É necessário o valor da transação!")
        Double valor,
        @NotNull(message = "É necessário ter a hora da transação!")
        LocalDateTime dataHora,
        @NotBlank(message = "É necessário ter um estabelecimento!")
        String estabelecimento) {
}