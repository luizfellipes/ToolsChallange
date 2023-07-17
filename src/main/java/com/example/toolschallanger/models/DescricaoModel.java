package com.example.toolschallanger.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

@Component
@Entity
@Table(name = "TB_DESCRICAO")
public class DescricaoModel implements Serializable {


    @Id
    private Long id;
    private Float valor;
    private LocalDateTime dataHora;
    private String estabelecimento;

    public DescricaoModel() {
    }

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
