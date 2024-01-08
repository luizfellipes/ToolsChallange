package com.example.toolschallanger.Mocks;

import com.example.toolschallanger.models.dtos.DescricaoRecordDTO;
import com.example.toolschallanger.models.dtos.FormaPagamentoRecordDTO;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.enuns.FormaPagamento;

import java.time.LocalDateTime;

public abstract class MocksDTO {

    public static TransacaoRecordDTO requestMockDTO() {
        return new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoRecordDTO responseMockDTO() {
        return new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
    }


}
