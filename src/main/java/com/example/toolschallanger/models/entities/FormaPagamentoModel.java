package com.example.toolschallanger.models.entities;


import com.example.toolschallanger.exceptions.RequestExceptionBadRequest;
import com.example.toolschallanger.exceptions.RequestExceptionNotFound;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//@Entity
//@Table(name = "TB_FORMA_PAGAMENTO")
public class FormaPagamentoModel {

    @Enumerated(EnumType.STRING)
    private FormaPagamento tipo;
    private Integer parcelas;
    private static final Logger log = LogManager.getLogger(FormaPagamentoModel.class);

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

    public void validaParcela(Double valor) {
        if (this.parcelas <= 1 || valor < 100) {
            if (this.tipo == FormaPagamento.PARCELADO_EMISSOR || this.tipo == FormaPagamento.PARCELADO_LOJA) {
                log.error("It was not possible to pay in installments for your purchase.");
                throw new RequestExceptionBadRequest("Não foi possivel parcelar sua compra, somente parcelas acima de 2x e valor acima de 100 !");
            }
        }
    }

}
