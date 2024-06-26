package com.example.toolschallanger.models.entities;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "TB_TRANSACAO")
public class TransacaoModel extends RepresentationModel<TransacaoModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Long cartao;
    @Embedded
    private DescricaoModel descricaoModel;
    @Embedded
    private FormaPagamentoModel formaPagamentoModel;

    public TransacaoModel() {
    }

    public TransacaoModel(Long cartao, DescricaoModel descricaoModel, FormaPagamentoModel formaPagamentoModel) {
        this.cartao = cartao;
        this.descricaoModel = descricaoModel;
        this.formaPagamentoModel = formaPagamentoModel;
        verificaFormaPagamentoParaIniciarValidaParcela();
    }

    public TransacaoModel(UUID id, Long cartao, DescricaoModel descricaoModel, FormaPagamentoModel formaPagamentoModel) {
        this.id = id;
        this.cartao = cartao;
        this.descricaoModel = descricaoModel;
        this.formaPagamentoModel = formaPagamentoModel;
        verificaFormaPagamentoParaIniciarValidaParcela();
    }

    public UUID getId() {
        return id;
    }

    public Long getCartao() {
        return cartao;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DescricaoModel getDescricaoModel() {
        return descricaoModel;
    }

    public FormaPagamentoModel getFormaPagamentoModel() {
        return formaPagamentoModel;
    }

    public void setCartao(Long cartao) {
        this.cartao = cartao;
    }

    public void setDescricaoModel(DescricaoModel descricaoModel) {
        this.descricaoModel = descricaoModel;
    }

    public void setFormaPagamentoModel(FormaPagamentoModel formaPagamentoModel) {
        this.formaPagamentoModel = formaPagamentoModel;
    }

    public void verificaFormaPagamentoParaIniciarValidaParcela() {
        if (this.formaPagamentoModel != null) {
            this.formaPagamentoModel.validaParcela();
        }
    }

}
