package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.example.toolschallanger.mocks.MocksModel.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class FormaPagamentoTest {


    private FormaPagamentoModel formaPagamentoModel;

    @BeforeEach
    void setUp() {
        this.formaPagamentoModel = mock(FormaPagamentoModel.class);
    }

    @Test
    void deveValidaParcelaSucesso() {
        formaPagamentoModel.validaParcela(500D);

        verify(formaPagamentoModel, times(1)).validaParcela(500D);

        Executable responseMockModelParceladoEmissor = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor());
        Executable responseMockModelParceladoLojaValorZero = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaValorZero().getDescricaoModel().getValor());
        Executable requestMockModel1 = () -> formaPagamentoModel.validaParcela(requestMockModel1().getDescricaoModel().getValor());

        Assertions.assertDoesNotThrow(responseMockModelParceladoEmissor);
        Assertions.assertDoesNotThrow(responseMockModelParceladoLojaValorZero);
        Assertions.assertDoesNotThrow(requestMockModel1);
    }

    @Test
    void deveDarErroAoValidarParcela() {
        formaPagamentoModel.validaParcela(500D);

        verify(formaPagamentoModel, times(1)).validaParcela(500D);
        Executable responseMockModelParceladoValorBaixo = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor());
        Executable responseMockModelParceladoLojaParcela1 = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaParcela1().getDescricaoModel().getValor());
        Executable responseMockModelAvistaParcela2 = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor());

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoValorBaixo);
        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoLojaParcela1);
        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelAvistaParcela2);
    }

}
