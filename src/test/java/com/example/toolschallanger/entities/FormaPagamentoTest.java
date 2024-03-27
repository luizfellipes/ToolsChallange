package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.example.toolschallanger.mocks.MocksModel.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
class FormaPagamentoTest {

    private FormaPagamentoModel formaPagamentoModel;

    @BeforeEach
    void setUp() {
        this.formaPagamentoModel = new FormaPagamentoModel();
    }

    @Test
    void deveValidaParceladoEmissorComSucesso() {
        Executable responseMockModelParceladoEmissor = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoEmissor().getDescricaoModel().getValor());

        Assertions.assertDoesNotThrow(responseMockModelParceladoEmissor);
    }

    @Test
    void deveValidarParceladoLojaComSucesso() {
        Executable responseMockModelParceladoLojaValorZero = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaValorZero().getDescricaoModel().getValor());

        Assertions.assertDoesNotThrow(responseMockModelParceladoLojaValorZero);
    }

    @Test
    void deveValidarAvistaComSucesso() {
        Executable requestMockModel1 = () -> formaPagamentoModel.validaParcela(requestMockModelAvista().getDescricaoModel().getValor());

        Assertions.assertDoesNotThrow(requestMockModel1);
    }

    @Test
    void deveDarErroAoValidarParcela() {
        Executable responseMockModelParceladoValorBaixo = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoValorBaixo().getDescricaoModel().getValor());

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoValorBaixo);
    }

    @Test
    void deveDarErroAoValidarParceladoLojaComParcelaBaixa(){
        Executable responseMockModelParceladoLojaParcela1 = () -> formaPagamentoModel.validaParcela(responseMockModelParceladoLojaParcela1().getDescricaoModel().getValor());

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoLojaParcela1);
    }

    @Test
    void deveDarErroAoValidarAvistaComParcelas(){
        Executable responseMockModelAvistaParcela2 = () -> formaPagamentoModel.validaParcela(responseMockModelAvistaParcela2().getDescricaoModel().getValor());

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelAvistaParcela2);
    }

}
