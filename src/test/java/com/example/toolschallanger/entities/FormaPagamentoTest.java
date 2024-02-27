package com.example.toolschallanger.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.toolschallanger.mocks.MocksModel.*;

@ExtendWith(SpringExtension.class)
public class FormaPagamentoTest {


    @Test
    public void deveValidaParcelaSucesso() {
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getFormaPagamentoModel().validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor()));
    }

    @Test
    public void deveDarErroAoValidarParcela() {
        Assertions.assertThrows(RuntimeException.class, () -> responseMockModelParceladoValorBaixo().getFormaPagamentoModel().validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor()));
    }

}
