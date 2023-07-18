package com.example.toolschallanger.Dtos;

import com.example.toolschallanger.models.TransacaoModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TransacaoRecordDto(@NotNull
                                 @JsonProperty
                                 TransacaoModel transacaoModel) {
}
