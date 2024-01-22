package com.example.toolschallanger.entities;

import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.example.toolschallanger.mocks.MocksModel.*;

@SpringBootTest
public class DescricaoModelTest {

    @Test
    public void deveTestarStatusAUTORIZADO() {
        Assertions.assertNotNull(responseMockModelParceladoEmissor());
        Assertions.assertEquals(Status.AUTORIZADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarStatusNEGADO() {
        Assertions.assertNotNull(responseMockModelParceladoEmissorValorZero());
        Assertions.assertEquals(Status.NEGADO, responseMockModelParceladoEmissorValorZero().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveDarErroStatusAutorizado() {
        Assertions.assertNotNull(responseMockModelParceladoEmissor());
        Assertions.assertNotEquals(Status.NEGADO, responseMockModelParceladoEmissor().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveDarErroStatusNegado() {
        Assertions.assertNotNull(responseMockModelParceladoEmissorValorZero());
        Assertions.assertNotEquals(Status.AUTORIZADO, responseMockModelParceladoEmissorValorZero().getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarVerificaValorNegativo() {
        Assertions.assertNotNull(responseMockModelParceladoEmissorValorNegativo());
        Assertions.assertThrows(RuntimeException.class, () -> responseMockModelParceladoEmissorValorNegativo().getDescricaoModel().verificaValorNegativo());
    }

    @Test
    public void deveDarErroAoVerificaValorNegativo() {
        Assertions.assertNotNull(responseMockModelParceladoEmissor());
        Assertions.assertDoesNotThrow(() -> responseMockModelParceladoEmissor().getDescricaoModel().verificaValorNegativo());
    }


}
