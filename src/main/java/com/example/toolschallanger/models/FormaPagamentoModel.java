package com.example.toolschallanger.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Component
@Entity
@Table(name = "TB_FORMA_PAGAMENTO")
public class FormaPagamentoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String tipo;
    private Integer parcelas;

    public FormaPagamentoModel() {
    }

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
