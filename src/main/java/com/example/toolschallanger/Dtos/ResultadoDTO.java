package com.example.toolschallanger.Dtos;

import org.springframework.stereotype.Component;

@Component
public class ResultadoDTO {
    private TransacaoRecordDto transacaoRecordDtoDto;
    private DescricaoRecordDto descricaoRecordDto;
    private FormaPagamentoRecordDto formaPagamentoDto;

    public TransacaoRecordDto getTransacaoRecordDtoDto() {
        return transacaoRecordDtoDto;
    }

    public void setTransacaoRecordDtoDto(TransacaoRecordDto transacaoRecordDtoDto) {
        this.transacaoRecordDtoDto = transacaoRecordDtoDto;
    }

    public DescricaoRecordDto getDescricaoRecordDto() {
        return descricaoRecordDto;
    }

    public void setDescricaoRecordDto(DescricaoRecordDto descricaoRecordDto) {
        this.descricaoRecordDto = descricaoRecordDto;
    }

    public FormaPagamentoRecordDto getFormaPagamentoDto() {
        return formaPagamentoDto;
    }

    public void setFormaPagamentoDto(FormaPagamentoRecordDto formaPagamentoDto) {
        this.formaPagamentoDto = formaPagamentoDto;
    }
}
