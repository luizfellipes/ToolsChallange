package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.toolschallanger.mocks.MocksModel.*;

@ExtendWith(SpringExtension.class)
class FormaPagamentoTest {


    //caso de sucesso
    @Test
    void deveValidaParceladoEmissorComSucesso() {
        Executable responseMockModelParceladoEmissor = () -> responseMockModelParceladoEmissor().getFormaPagamentoModel().validaParcela();

        Assertions.assertDoesNotThrow(responseMockModelParceladoEmissor);
    }

    @Test
    void deveValidarParceladoLojaComSucesso() {
        Executable responseMockModelParceladoLojaValorZero = () -> responseMockModelParceladoLojaValorZero().getFormaPagamentoModel().validaParcela();

        Assertions.assertDoesNotThrow(responseMockModelParceladoLojaValorZero);
    }

    @Test
    void deveValidarAvistaComSucesso() {
        Executable requestMockModelAvista = () -> requestMockModelAvista().getFormaPagamentoModel().validaParcela();

        Assertions.assertDoesNotThrow(requestMockModelAvista);
    }


    //Caso de erro
    @Test
    void deveDarErroAoValidarParcela() {
        Executable responseMockModelParceladoValorBaixo = () -> responseMockModelParceladoValorBaixo().getFormaPagamentoModel().validaParcela();

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoValorBaixo);
    }

    @Test
    void deveDarErroAoValidarParceladoLojaComParcelaBaixa() {
        Executable responseMockModelParceladoLojaParcela1 = () -> responseMockModelParceladoLojaParcela1().getFormaPagamentoModel().validaParcela();

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoLojaParcela1);
    }

    @Test
    void deveDarErroAoValidarAvistaComParcelas() {
        Executable responseMockModelAvistaParcela2 = () -> responseMockModelAvistaParcela2().getFormaPagamentoModel().validaParcela();

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelAvistaParcela2);
    }

}
