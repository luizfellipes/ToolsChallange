package com.example.toolschallanger.Dtos;


import jakarta.validation.constraints.NotNull;


public record TransacaoRecordDto(@NotNull Long card) {
}