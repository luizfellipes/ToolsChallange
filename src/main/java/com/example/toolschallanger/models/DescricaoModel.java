package com.example.toolschallanger.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DescricaoModel {

    @NotNull
    private Float valor;
    @NotNull
    private LocalDateTime dataHora;
    @NotBlank
    private String estabelecimento;


    public Float getValor() {
        return valor;
    }
    public void setValor(Float valor) {this.valor = valor;}

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
