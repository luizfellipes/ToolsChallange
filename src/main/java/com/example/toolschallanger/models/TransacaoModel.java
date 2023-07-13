package com.example.toolschallanger.models;


import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Entity
@Table(name = "TB_TRASANCAO")
public class TransacaoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCard;
    @NotNull
    private Long card;
    @NotNull
    private Float valor;
    @NotNull
    private LocalDateTime dataHora;
    @NotBlank
    private String estabelecimento;
    @NotBlank
    private String tipo;
    @NotNull
    private int parcelas;

    public TransacaoModel() {
    }

    public TransacaoModel(TransacaoRecordDto dto) {
        this.card = dto.card();
        this.valor = dto.valor();
        this.dataHora = conversorDataHora(dto.dataHora());
        this.estabelecimento = dto.estabelecimento();
        this.tipo = dto.tipo();
        this.parcelas = dto.parcelas();
    }

    private LocalDateTime conversorDataHora(CharSequence dataHoraDto){
        return dataHora = LocalDateTime.parse(dataHoraDto, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public Long getCard() {
        return card;
    }

    public UUID getIdCard() {
        return idCard;
    }

    public Float getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public String getTipo() {
        return tipo;
    }

    public int getParcelas() {
        return parcelas;
    }

}
