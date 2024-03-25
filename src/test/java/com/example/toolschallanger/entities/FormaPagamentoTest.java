package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.services.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        Assertions.assertDoesNotThrow(() -> formaPagamentoModel.validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor()));
        Assertions.assertDoesNotThrow(() -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaValorZero().getDescricaoModel().getValor()));
        Assertions.assertDoesNotThrow(() -> formaPagamentoModel.validaParcela(requestMockModel1().getDescricaoModel().getValor()));
    }

    @Test
    void deveDarErroAoValidarParcela() {
        formaPagamentoModel.validaParcela(500D);

        verify(formaPagamentoModel, times(1)).validaParcela(500D);

        Assertions.assertThrows(TransacaoBadRequest.class, () -> formaPagamentoModel.validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor()));
        Assertions.assertThrows(TransacaoBadRequest.class, () -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaParcela1().getDescricaoModel().getValor()));
        Assertions.assertThrows(TransacaoBadRequest.class, () -> formaPagamentoModel.validaParcela(responseMockModelAvistaParcela2().getDescricaoModel().getValor()));
    }

}
