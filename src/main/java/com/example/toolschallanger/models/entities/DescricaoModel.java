package com.example.toolschallanger.models.entities;

import com.example.toolschallanger.exceptions.RequestExceptionBadRequest;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger log = LogManager.getLogger(DescricaoModel.class);

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
            log.info("Error generating NSU.");
        } else {
            this.nsu = Math.floor(Math.random() * 1000);
            log.info("NSU generating successfully.");
        }
    }

    public void geraCodigoAutorizacaoValido() {
        if (this.valor <= 0.0) {
            this.codigoAutorizacao = 0D;
            log.info("Error generating authorization code.");
        } else {
            this.codigoAutorizacao = Math.floor(Math.random() * 1000);
            log.info("Authorization code generating successfully.");
        }
    }

    public Status verificaStatus() {
        if (this.valor <= 0.0) {
            log.info("Error generating the status.");
            return this.status = Status.NEGADO;
        } else {
            log.info("Status generating successfully.");
            return this.status = Status.AUTORIZADO;
        }
    }

    public void verificaValorNegativo() {
        if (this.valor < 0.0) {
            log.error("Negative values are not allowed.");
            throw new RequestExceptionBadRequest("Valores negativos não são permitidos !");
        }
    }

    public void geraValoresValidos() {
        if (this.nsu == null && this.codigoAutorizacao == null && this.status == null) {
            verificaValorNegativo();
            geraNsuValido();
            geraCodigoAutorizacaoValido();
            verificaStatus();
            log.info("Generating new valid values.");
        } else {
            this.nsu = getNsu();
            this.codigoAutorizacao = getCodigoAutorizacao();
            this.status = getStatus();
            log.info("Maintained existing values.");
        }
    }

}

