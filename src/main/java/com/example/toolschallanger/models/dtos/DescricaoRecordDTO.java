package com.example.toolschallanger.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DescricaoRecordDTO(
        @NotNull(message = "Um valor minimo deve ser inserido para a transação.")
        Double valor,
        @Valid
        @NotNull(message = "A data e hora da transação devem ser inserida.")
        LocalDateTime dataHora,
        @Valid
        @NotBlank(message = "O nome do estabelecimento precisa ser preenchido.")
        String estabelecimento) {

}