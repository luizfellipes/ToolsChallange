package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.toolschallanger.mocks.MocksModel.*;

@ExtendWith(SpringExtension.class)
class FormaPagamentoTest {


    @Test
    void deveValidaParcelaSucesso() {
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getFormaPagamentoModel().validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor()));
    }

    @Test
    void deveDarErroAoValidarParcela() {
        Assertions.assertThrows(TransacaoBadRequest.class, () -> responseMockModelParceladoValorBaixo().getFormaPagamentoModel().validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor()));
    }

}
