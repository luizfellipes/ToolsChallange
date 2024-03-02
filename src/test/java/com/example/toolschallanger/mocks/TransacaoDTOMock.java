package com.example.toolschallanger.mocks;

import com.example.toolschallanger.models.dtos.DescricaoRecordDTO;
import com.example.toolschallanger.models.dtos.FormaPagamentoRecordDTO;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.enuns.FormaPagamento;

import java.time.LocalDateTime;

public abstract class TransacaoDTOMock {

    public static TransacaoRecordDTO mockDTORequest() {
        return new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoRecordDTO mockDTOResponse() {
        return new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
    }
}
