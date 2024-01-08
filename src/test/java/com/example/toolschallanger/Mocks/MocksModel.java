package com.example.toolschallanger.Mocks;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class MocksModel {

    public static TransacaoModel requestMockModel(){
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                        new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoModel responseMockModel(){
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                        new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.CANCELADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }

}
