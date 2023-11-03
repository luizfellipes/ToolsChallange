package com.example.toolschallanger.models.entities;


import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

//@Entity
//@Table(name = "TB_FORMA_PAGAMENTO")
public class FormaPagamentoModel {

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

    public void setTipo(FormaPagamento tipo) {
        this.tipo = tipo;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public void validaParcela(TransacaoModel transacaoModel) {
        if (this.parcelas <= 1 || transacaoModel.getDescricaoModel().getValor() < 100) {
            if (this.tipo == FormaPagamento.PARCELADO_EMISSOR || this.tipo == FormaPagamento.PARCELADO_LOJA) {
                throw new RuntimeException("Não foi possivel parcelar sua compra !");
            }
        }
    }

}
