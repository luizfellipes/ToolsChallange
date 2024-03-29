package com.example.toolschallanger.entities;

import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.toolschallanger.mocks.MocksModel.*;

@SpringBootTest
public class DescricaoModelTest {

    @Test
    public void deveTestarStatusAUTORIZADO() {
        Assertions.assertEquals(Status.AUTORIZADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarStatusNEGADO() {
        Assertions.assertEquals(Status.NEGADO, responseMockModelStatusNegado().getDescricaoModel().getStatus());
    }

    @Test
    public void deveDarErroStatusAutorizado() {
        Assertions.assertNotEquals(Status.NEGADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveDarErroStatusNegado() {
        Assertions.assertNotEquals(Status.AUTORIZADO, responseMockModelStatusNegado().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarVerificaValorNegativo() {
        Assertions.assertThrows(RuntimeException.class, () -> responseMockModelParceladoEmissorValorNegativo().getDescricaoModel().verificaValorNegativo());
    }

    @Test
    public void deveDarErroAoVerificaValorNegativo() {
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getDescricaoModel().verificaValorNegativo());
    }


}
