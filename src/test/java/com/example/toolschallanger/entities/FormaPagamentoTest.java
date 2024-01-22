package com.example.toolschallanger.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.toolschallanger.mocks.MocksModel.*;

@SpringBootTest
public class FormaPagamentoTest {

    @Test
    public void deveValidaParcelaSucesso() {
        Assertions.assertNotNull(responseMockModelParceladoEmissor());
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getFormaPagamentoModel().validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor()));
    }

    @Test
    public void deveDarErroAoValidarParcela() {
        Assertions.assertNotNull(responseMockModelParceladoValorBaixo());
        Assertions.assertThrows(RuntimeException.class, () -> responseMockModelParceladoValorBaixo().getFormaPagamentoModel().validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor()));
    }

}
