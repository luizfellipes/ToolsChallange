package com.example.toolschallanger.Dtos;

import jakarta.validation.constraints.NotBlank;

public record TransacaoRecordDto(@NotBlank Long card) {
}
