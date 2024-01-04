package com.example.toolschallanger.ServiceTest;

import com.example.toolschallanger.models.dtos.DescricaoRecordDTO;
import com.example.toolschallanger.models.dtos.FormaPagamentoRecordDTO;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
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
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L, new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        TransacaoModel transacaoSave = transacaoService.save(transacaoRecordDto);
        Assertions.assertEquals(transacaoRecordDto, transacaoSave.toString());
    }

    @Test
    public void deveTestarEstorno() {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        Assertions.assertEquals(Status.CANCELADO, transacaoService.estorno(transacaoRecordDto).getDescricaoModel().getStatus());
    }

    //Caso de falha
    @Test
    public void deveTestarSave_EmCasoDeFalha() {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(null, null, null);
        Assertions.assertThrows(RuntimeException.class, ()-> transacaoService.save(transacaoRecordDto));
    }

    @Test
    public void deveTestarEstorno_EmCasoDeFalha() {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(null, null, null);
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(transacaoRecordDto));
    }

}
