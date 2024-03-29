package com.example.toolschallanger.models.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.enuns.Status;
import jakarta.persistence.*;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDateTime;


//@Entity
//@Table(name = "TB_DESCRICAO")
public class DescricaoModel implements Serializable {

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
        this.verificaValorNegativo();
    }

    public DescricaoModel(Double valor, LocalDateTime dataHora, String estabelecimento, Double nsu, Double codigoAutorizacao, Status status) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.estabelecimento = estabelecimento;
        this.nsu = nsu;
        this.codigoAutorizacao = codigoAutorizacao;
        this.status = status;
        this.geraValoresValidos();
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

    public Double geraValoresNsuECodigoAutorizacao() {
        SecureRandom random = new SecureRandom();
        return this.valor >= 0.1 ? Math.floor(random.nextDouble() * 1000.0) : null;
    }

    public Status verificaStatus() {
        return this.valor >= 0.1 ? Status.AUTORIZADO : Status.NEGADO;
    }

    public void verificaValorNegativo() {
        if (this.valor != null && this.valor < 0.0) {
            throw new TransacaoBadRequest("Valores negativos não são permitidos !");
        }
    }

    public void geraValoresValidos() {
        if (this.nsu == null || this.codigoAutorizacao == null || this.status == null) {
            this.verificaValorNegativo();
            this.nsu = geraValoresNsuECodigoAutorizacao();
            this.codigoAutorizacao = geraValoresNsuECodigoAutorizacao();
            this.status = verificaStatus();
        }
    }

}

