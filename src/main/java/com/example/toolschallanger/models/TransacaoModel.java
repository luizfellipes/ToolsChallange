package com.example.toolschallanger.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Entity
@Table(name = "TB_TRASANCAO")
public class TransacaoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCard;
    private Long card;
    private DescricaoModel descricaoModel;
    private FormaPagamentoModel formaPagamentoModel;


    public TransacaoModel() {
    }

    public UUID getIdCard() {
        return idCard;
    }

    public void setIdCard(UUID idCard) {
        this.idCard = idCard;
    }

    public Long getCard() {
        return card;
    }

    public void setCard(Long card) {
        this.card = card;
    }

    public DescricaoModel getDescricaoModel() {
        return descricaoModel;
    }

    public void setDescricaoModel(DescricaoModel descricaoModel) {
        this.descricaoModel = descricaoModel;
    }

    public FormaPagamentoModel getFormaPagamentoModel() {
        return formaPagamentoModel;
    }

    public void setFormaPagamentoModel(FormaPagamentoModel formaPagamentoModel) {
        this.formaPagamentoModel = formaPagamentoModel;
    }

}
