package com.example.toolschallanger.Service;

import com.example.toolschallanger.models.dtos.DescricaoRecordDTO;
import com.example.toolschallanger.models.dtos.FormaPagamentoRecordDTO;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.UUID;

import static com.example.toolschallanger.mocks.MocksDTO.*;
import static com.example.toolschallanger.mocks.MocksDTO.requestMockDTO;
import static com.example.toolschallanger.mocks.MocksModel.requestMockModel;

@SpringBootTest
@ActiveProfiles("test")
public class TransacaoServiceTest {

    @Autowired
    private TransacaoService transacaoService;

    //Caso de sucesso
    @Test
    public void deveTestarSave() {
        TransacaoModel savedTransacaoModel = transacaoService.save(responseMockDTO());
        TransacaoRecordDTO savedTransacaoRecordDto = new TransacaoRecordDTO(
                savedTransacaoModel.getCartao(),
                new DescricaoRecordDTO(savedTransacaoModel.getDescricaoModel().getValor(),
                        savedTransacaoModel.getDescricaoModel().getDataHora(),
                        savedTransacaoModel.getDescricaoModel().getEstabelecimento()),
                new FormaPagamentoRecordDTO(savedTransacaoModel.getFormaPagamentoModel().getTipo(),
                        savedTransacaoModel.getFormaPagamentoModel().getParcelas()));
        Assertions.assertEquals(responseMockDTO(), savedTransacaoRecordDto);
    }

    @Test
    public void deveTestarEstorno() {
        TransacaoModel transacaoModel = transacaoService.save(requestMockDTO());
        Assertions.assertEquals(Status.CANCELADO, transacaoService.estorno(transacaoModel.getId(), requestMockDTO()).getDescricaoModel().getStatus());
    }

    //Caso de falha
    @Test
    public void deveTestarSave_EmCasoDeFalha() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.save(requestMockNullDTO()));
    }

    @Test
    public void deveTestarEstorno_EmCasoDeFalha() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(UUID.randomUUID(), requestMockNullDTO()));
    }

}
