package com.example.toolschallanger.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CARDS")
public class TransacaoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCard;
    private Long card;

    public Long getCartao() {
        return card;
    }

    public void setCartao(Long cartao) {
        this.card = cartao;
    }

    public UUID getIdCard() {
        return idCard;
    }

    public void setIdCard(UUID idCard) {
        this.idCard = idCard;
    }
}
