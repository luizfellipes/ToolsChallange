package com.example.toolschallanger.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Entity
@Table(name = "TB_FORMAPAGAMENTO")
public class FormaPagamentoModel implements Serializable {


    @Id
    private Long id;
    private String tipo;

    private Integer parcelas;


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }
}
