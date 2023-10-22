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
    @Enumerated
    private Status status;

    public DescricaoModel() {
    }

    public DescricaoModel(Double valor, LocalDateTime dataHora, String estabelecimento) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        geraNsuValido();
        geraCodigoAutorizacaoValido();
        verificaStatus();
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


    public void geraNsuValido(){
        if (valor < 0) {
            this.nsu = 00000.00;
        }else {
            this.nsu = Math.random();
        }
    }

    public void geraCodigoAutorizacaoValido(){
        if (valor < 0) {
            this.codigoAutorizacao = 00000.00;
        }else {
            this.codigoAutorizacao = Math.random();
        }
    }

    public void verificaStatus() {
        if (valor < 0) {
            this.status = Status.NEGADO;
        } else {
            this.status = Status.AUTORIZADO;
        }
    }




}

