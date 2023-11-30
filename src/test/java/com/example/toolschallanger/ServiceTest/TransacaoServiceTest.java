package com.example.toolschallanger.ServiceTest;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

    @Autowired
    TransacaoService transacaoService;

    //Caso de sucesso
    @Test
    public void deveTestarSave() {
        TransacaoModel transacaoModel = (
                new TransacaoModel(1065151L,
                        new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        TransacaoModel trasacaoSave = transacaoService.save(transacaoModel);
        Assertions.assertEquals(transacaoModel, trasacaoSave);
    }

    @Test
    public void deveTestarEstorno() {
        TransacaoModel transacaoModel = (
                new TransacaoModel(1065151L,
                        new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        transacaoService.estorno(transacaoModel);
        Assertions.assertEquals(Status.CANCELADO, transacaoModel.getDescricaoModel().getStatus());
    }

    //Caso de falha
    @Test
    public void deveTestarSave_EmCasoDeFalha() {
        TransacaoModel transacaoModel = new TransacaoModel();
        Assertions.assertThrows(RuntimeException.class, ()-> transacaoService.save(transacaoModel));
    }

    @Test
    public void deveTestarEstorno_EmCasoDeFalha() {
        TransacaoModel transacaoModel = (
                new TransacaoModel(1065151L,
                        new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.CANCELADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(transacaoModel));
    }

}
