package com.example.toolschallanger.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "TB_TRANSACAO")
public class TransacaoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull(message = "O Cartão não pode ser vazio ou nulo")
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
    }

    public TransacaoModel(UUID id, Long cartao, DescricaoModel descricaoModel, FormaPagamentoModel formaPagamentoModel) {
        this.id = id;
        this.cartao = cartao;
        this.descricaoModel = descricaoModel;
        this.formaPagamentoModel = formaPagamentoModel;
    }

    public UUID getId() {
        return id;
    }

    public Long getCartao() {
        return cartao;
    }

    public DescricaoModel getDescricaoModel() {
        return descricaoModel;
    }

    public FormaPagamentoModel getFormaPagamentoModel() {
        return formaPagamentoModel;
    }


}
