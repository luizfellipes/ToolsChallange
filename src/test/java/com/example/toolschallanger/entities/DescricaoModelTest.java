package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.toolschallanger.mocks.MocksModel.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DescricaoModelTest {

    private DescricaoModel descricaoModel;

    @BeforeEach
    void setUp() {
        this.descricaoModel = mock(DescricaoModel.class);
    }


    @Test
    void deveTestarGeraNsuValido() {
        Assertions.assertNotEquals(null, requestMockModel().getDescricaoModel().geraValoresNsuECodigoAutorizacao());
    }

    @Test
    void deveTestarGeraCodigoAutorizacaoValido() {
        Assertions.assertNotEquals(null, requestMockModel().getDescricaoModel().geraValoresNsuECodigoAutorizacao());
    }

    @Test
    void deveTestarStatusAUTORIZADO() {
        Assertions.assertEquals(Status.AUTORIZADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    void deveTestarStatusNEGADO() {
        Assertions.assertEquals(Status.NEGADO, responseMockModelStatusNegado().getDescricaoModel().getStatus());
    }

    @Test
    void deveTestarVerificaValorNegativo() {
        Executable responseMockModelParceladoEmissor = () -> responseMockModelParceladoEmissor().getDescricaoModel().verificaValorNegativo();

        Assertions.assertDoesNotThrow(responseMockModelParceladoEmissor);
    }

    @Test
    void deveTestarValoresValidos() {
        descricaoModel.geraValoresValidos();

        verify(descricaoModel, times(1)).geraValoresValidos();

        DescricaoModel descricaoModel = new DescricaoModel(20D, null, null, null, null, null);

        Assertions.assertNotNull(descricaoModel.getNsu());
        Assertions.assertNotNull(descricaoModel.getCodigoAutorizacao());
        Assertions.assertNotNull(descricaoModel.getStatus());
    }

    //Caso de erro
    @Test
    void deveDarErroAoGeraNsuValido() {
        TransacaoModel transacaoModel = requestMockModel1();
        transacaoModel.getDescricaoModel().setValor(0D);

        Assertions.assertNull(transacaoModel.getDescricaoModel().geraValoresNsuECodigoAutorizacao());
    }

    @Test
    void deveDarErroAoGerarCodigoAutorizacao() {
        TransacaoModel transacaoModel = responseMockModelCodigoAutorizacaoNull();

        Assertions.assertNull(transacaoModel.getDescricaoModel().getCodigoAutorizacao());
    }

    @Test
    void deveDarErroStatusAutorizado() {
        Assertions.assertNotEquals(Status.NEGADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    void deveDarErroStatusNegado() {
        Assertions.assertNotEquals(Status.AUTORIZADO, responseMockModelStatusNegado().getDescricaoModel().verificaStatus());
    }

    @Test
    void deveDarErroAoVerificaValorNegativo() {
        Executable responseMockModelParceladoEmissorValorNegativo = () -> responseMockModelParceladoEmissorValorNegativo().getDescricaoModel().verificaValorNegativo();

        Assertions.assertThrows(TransacaoBadRequest.class, responseMockModelParceladoEmissorValorNegativo);
    }

    @Test
    void deveDarErroAoGerarValoresMantendoOExistente() {
        descricaoModel.geraValoresValidos();

        verify(descricaoModel, times(1)).geraValoresValidos();

        DescricaoModel descricaoModel = new DescricaoModel();

        Assertions.assertNull(descricaoModel.getNsu());
        Assertions.assertNull(descricaoModel.getCodigoAutorizacao());
        Assertions.assertNull(descricaoModel.getStatus());
    }

}
