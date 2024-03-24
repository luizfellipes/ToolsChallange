package com.example.toolschallanger.entities;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.toolschallanger.mocks.MocksModel.*;

@SpringBootTest
class DescricaoModelTest {

    @Test
    void deveTestarStatusAUTORIZADO() {
        Assertions.assertEquals(Status.AUTORIZADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    void deveTestarStatusNEGADO() {
        Assertions.assertEquals(Status.NEGADO, responseMockModelStatusNegado().getDescricaoModel().getStatus());
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
