package com.example.toolschallanger.entitiesTest;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.toolschallanger.models.enuns.FormaPagamento.PARCELADO_EMISSOR;

@SpringBootTest
@ActiveProfiles("test")
public class DescricaoModelTest {

    @Test
    public void deveTestarStatus() {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
        Assertions.assertNotNull(transacaoModel);
        Assertions.assertEquals(Status.AUTORIZADO, transacaoModel.getDescricaoModel().verificaStatus());
        transacaoModel.getDescricaoModel().setValor(0D);
        Assertions.assertEquals(Status.NEGADO, transacaoModel.getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarStatus_CasoDeFalha() {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
        Assertions.assertNotNull(transacaoModel);
        Assertions.assertNotEquals(Status.NEGADO, transacaoModel.getDescricaoModel().verificaStatus());
        transacaoModel.getDescricaoModel().setValor(0D);
        Assertions.assertNotEquals(Status.AUTORIZADO, transacaoModel.getDescricaoModel().verificaStatus());
    }

    @Test
    public void deveTestarVerificaValorNegativo(){
        TransacaoModel transacaoModel = new TransacaoModel();
        Assertions.assertThrows(RuntimeException.class, () -> transacaoModel.getDescricaoModel().verificaValorNegativo());
    }

    @Test
    public void deveTestarVerificaValorNegativo_CasoDeFalha(){
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
        Assertions.assertDoesNotThrow(() -> transacaoModel.getDescricaoModel().verificaValorNegativo());
    }


}
