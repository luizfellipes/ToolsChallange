package com.example.toolschallanger.models.entities;


import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.persistence.*;

import java.io.Serializable;


//@Entity
//@Table(name = "TB_FORMA_PAGAMENTO")
public class FormaPagamentoModel implements Serializable {

    @Enumerated(EnumType.STRING)
    private FormaPagamento tipo;
    private Integer parcelas;

    public FormaPagamentoModel() {
    }

    public FormaPagamentoModel(FormaPagamento tipo, Integer parcelas) {
        this.tipo = tipo;
        this.parcelas = parcelas;
    }

    public FormaPagamento getTipo() {
        return tipo;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setTipo(FormaPagamento tipo) {
        this.tipo = tipo;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public void validaParcela() {
        if (this.tipo == FormaPagamento.PARCELADO_EMISSOR && this.parcelas <= 1 || this.tipo == FormaPagamento.PARCELADO_LOJA && this.parcelas <= 1) {
            throw new TransacaoBadRequest("Não foi possivel parcelar sua compra, somente parcelas acima de 2x e valor acima de 100 !");
        }
        if (this.tipo == FormaPagamento.AVISTA && this.parcelas > 1) {
            throw new TransacaoBadRequest("Não foi possivel realizar sua compra, não é possivel parcelar no modo avista !");
        }
    }

}
