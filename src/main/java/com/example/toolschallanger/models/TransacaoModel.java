package com.example.toolschallanger.models;


import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import jakarta.persistence.*;
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
    private DescricaoModel descricaoModel;

    private FormaPagamento formaPagamento;


    public TransacaoModel() {
    }

    public TransacaoModel(TransacaoRecordDto dto) {
        this.card = dto.card();
        descricaoModelValor(dto.valor());
        conversorDataHora(dto.dataHora());
        descricaoModelEstabelecimento(dto.estabelecimento());
        formaPagamentoTipo(dto.tipo());
        formaPagamentoParcelas(dto.parcelas());
    }

    private void descricaoModelValor(Float valorDto) {
        this.descricaoModel.setValor(valorDto);
    }

    private void conversorDataHora(CharSequence dataHoraDto) {
        this.descricaoModel.setDataHora(LocalDateTime.parse(dataHoraDto, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    private void descricaoModelEstabelecimento(String estabelecimentoDto){
        this.descricaoModel.setEstabelecimento(estabelecimentoDto);
    }

    private void formaPagamentoTipo(String tipoDto){
        this.formaPagamento.setTipo(tipoDto);
    }

    private void formaPagamentoParcelas(int parcelasDto){
        this.formaPagamento.setParcelas(parcelasDto);
    }
}
