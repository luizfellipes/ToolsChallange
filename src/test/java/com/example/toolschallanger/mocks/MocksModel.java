package com.example.toolschallanger.mocks;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.toolschallanger.models.enuns.FormaPagamento.*;

public abstract class MocksModel {

    public static TransacaoModel requestMockModel() {
        return new TransacaoModel(UUID.fromString("8e15652a-47e3-4d3d-bdb3-6b58ec871d74"), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 0000.1111, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }
    public static TransacaoModel requestMockModelAvista() {
        return new TransacaoModel(UUID.fromString("8e15652a-47e3-4d3d-bdb3-6b58ec871d74"), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoModel requestNullMockModel() {
        return new TransacaoModel(UUID.randomUUID(), null,
                new DescricaoModel(null, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 0.0, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoModel responseMockModel() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.CANCELADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
    }

    public static TransacaoModel responseMockModelParceladoEmissor() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
    }
    public static TransacaoModel responseMockModelParceladoLojaParcela1() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(00.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(PARCELADO_LOJA, 1));
    }

    public static TransacaoModel responseMockModelParceladoLojaValorZero() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(0.0, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(PARCELADO_LOJA , 2));
    }

    public static TransacaoModel responseMockModelStatusNegado() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(0.0, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.NEGADO),
                new FormaPagamentoModel(AVISTA, 1));
    }

    public static TransacaoModel responseMockModelParceladoEmissorValorNegativo() {
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(-10.0, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
    }

    public static TransacaoModel responseMockModelParceladoValorBaixo(){
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(PARCELADO_EMISSOR, 1));
    }

    public static TransacaoModel responseMockModelCodigoAutorizacaoNull(){
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(0.0, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", null, null, null),
                new FormaPagamentoModel(AVISTA, 1));
    }
    public static TransacaoModel responseMockModelAvistaParcela2(){
        return new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(0.0, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", null, null, null),
                new FormaPagamentoModel(AVISTA, 2));
    }

}
