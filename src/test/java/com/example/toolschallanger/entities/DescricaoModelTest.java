package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.example.toolschallanger.mocks.MocksModel.*;

@ExtendWith(SpringExtension.class)
class DescricaoModelTest {

    @Test
    void deveTestarGeraNsuValido() {
        Assertions.assertNotEquals(null, requestMockModel().getDescricaoModel().geraNsuValido());
    }
    @Test
    void deveTestarGeraCodigoAutorizacaoValido() {
        Assertions.assertNotEquals(null, requestMockModel().getDescricaoModel().geraCodigoAutorizacaoValido());
    }

    @Test
    void deveTestarStatusAUTORIZADO() {
        Assertions.assertEquals(Status.AUTORIZADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    void deveTestarStatusNEGADO() {
        Assertions.assertEquals(Status.NEGADO, responseMockModelStatusNegado().getDescricaoModel().getStatus());
    }

    //Caso de erro
    @Test
    void deveDarErroAoGeraNsuValido() {
        TransacaoModel transacaoModel = requestMockModel();
        transacaoModel.getDescricaoModel().setValor(0D);

        Assertions.assertNull(transacaoModel.getDescricaoModel().geraNsuValido());
    }

    @Test
    void deveDarErroAoGerarCodigoAutorizacao() {
        TransacaoModel transacaoModel = responseMockModelCodigoAutorizacaoNull();
        transacaoModel.getDescricaoModel().setValor(0D);

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
    void deveTestarVerificaValorNegativo() {
        Assertions.assertThrows(TransacaoBadRequest.class, () -> responseMockModelParceladoEmissorValorNegativo().getDescricaoModel().verificaValorNegativo());
    }

    @Test
    void deveDarErroAoVerificaValorNegativo() {
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getDescricaoModel().verificaValorNegativo());
    }

}
