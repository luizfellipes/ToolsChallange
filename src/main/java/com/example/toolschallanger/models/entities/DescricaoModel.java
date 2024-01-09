package com.example.toolschallanger.models.entities;

import com.example.toolschallanger.models.enuns.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;


//@Entity
//@Table(name = "TB_DESCRICAO")
public class DescricaoModel {

    @NotBlank(message = "É necessario ter um valor maior que 0 !")
    private Double valor;
    @NotBlank(message = "É necessario ter a hora da transação !")
    private LocalDateTime dataHora;
    @NotBlank(message = "É necessario ter um estabelecimento !")
    private String estabelecimento;
    private Double nsu;
    private Double codigoAutorizacao;
    @Enumerated(EnumType.STRING)
    private Status status;

    public DescricaoModel() {
    }

    public DescricaoModel(Double valor, LocalDateTime dataHora, String estabelecimento) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
    }

    public DescricaoModel(Double valor, LocalDateTime dataHora, String estabelecimento, Double nsu, Double codigoAutorizacao, Status status) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        this.nsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public Double getNsu() {
        return nsu;
    }

    public Double getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setNsu(Double nsu) {
        this.nsu = nsu;
    }

    public void setCodigoAutorizacao(Double codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void geraNsuValido() {
        if (this.valor <= 0) {
            this.nsu = 0D;
        } else {
            this.nsu = Math.random();
        }
    }

    public void geraCodigoAutorizacaoValido() {
        if (this.valor <= 0) {
            this.codigoAutorizacao = 0D;
        } else {
            this.codigoAutorizacao = Math.random();
        }
    }

    public Status verificaStatus() {
        if (this.valor <= 0) {
            return this.status = Status.NEGADO;
        } else {
            return this.status = Status.AUTORIZADO;
        }
    }

    public void verificaValorNegativo() {
        if (this.valor < 0) {
            throw new RuntimeException("Valores negativos não são permitidos !");
        }
    }

    public void geraValoresAutomatico() {
        verificaValorNegativo();
        geraNsuValido();
        geraCodigoAutorizacaoValido();
        verificaStatus();
    }

}

