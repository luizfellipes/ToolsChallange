package com.example.toolschallanger.models.entities;

import com.example.toolschallanger.models.enuns.Status;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


//@Entity
//@Table(name = "TB_DESCRICAO")
public class DescricaoModel {

    private Double valor;
    private LocalDateTime dataHora;
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
            this.codigoAutorizacao = 00000.00;
        } else {
            this.codigoAutorizacao = Math.random();
        }
    }

    public void verificaStatus() {
        if (this.valor <= 0) {
            this.status = Status.NEGADO;
        } else {
            this.status = Status.AUTORIZADO;
        }
    }

    public void geraValoresAutomatico(){
        geraNsuValido();
        geraCodigoAutorizacaoValido();
        verificaStatus();
    }


}

