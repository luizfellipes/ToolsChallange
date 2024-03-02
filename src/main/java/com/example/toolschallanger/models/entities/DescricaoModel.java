package com.example.toolschallanger.models.entities;

import com.example.toolschallanger.models.enuns.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;


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
        geraValoresValidos();
    }

    public DescricaoModel(Double valor, LocalDateTime dataHora, String estabelecimento, Double nsu, Double codigoAutorizacao, Status status) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        this.nsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
        geraValoresValidos();
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setNsu(Double nsu) {
        this.nsu = nsu;
    }

    public void setCodigoAutorizacao(Double codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public void geraNsuValido() {
        if (this.valor <= 0.0) {
            this.nsu = 0D;
        } else {
            this.nsu = Math.floor(Math.random() * 1000);
        }
    }

    public void geraCodigoAutorizacaoValido() {
        if (this.valor <= 0.0) {
            this.codigoAutorizacao = 0D;
        } else {
            this.codigoAutorizacao = Math.floor(Math.random() * 1000);
        }
    }

    public Status verificaStatus() {
        if (this.valor <= 0.0) {
            return this.status = Status.NEGADO;
        } else {
            return this.status = Status.AUTORIZADO;
        }
    }

    public void verificaValorNegativo() {
        if (this.valor < 0.0) {
            throw new IllegalArgumentException("Valores negativos não são permitidos !");
        }
    }

    public void geraValoresValidos() {
        if (this.nsu == null && this.codigoAutorizacao == null && this.status == null) {
            verificaValorNegativo();
            geraNsuValido();
            geraCodigoAutorizacaoValido();
            verificaStatus();
        } else {
            this.nsu = getNsu();
            this.codigoAutorizacao = getCodigoAutorizacao();
            this.status = getStatus();
        }
    }

}

