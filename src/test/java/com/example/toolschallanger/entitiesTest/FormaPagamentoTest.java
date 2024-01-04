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
public class FormaPagamentoTest {

    @Test
    public void deveValidaParcelaSucesso() {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 2));
        Assertions.assertNotNull(transacaoModel);
        Assertions.assertDoesNotThrow(() -> transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel.getDescricaoModel().getValor()));
    }

    @Test
    public void deveValidaParcelaErro() {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 1));
        Assertions.assertNotNull(transacaoModel);
        Assertions.assertThrows(RuntimeException.class, () -> transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel.getDescricaoModel().getValor()));
    }

}
