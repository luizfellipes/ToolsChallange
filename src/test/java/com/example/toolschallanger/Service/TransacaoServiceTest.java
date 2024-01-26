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


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.toolschallanger.mocks.MocksDTO.*;
import static com.example.toolschallanger.mocks.MocksDTO.requestMockDTO;

@SpringBootTest
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

    @Test
    public void deveTestarFindAll() {
        transacaoService.save(requestMockDTO());
        List<TransacaoModel> transacaoModelSalvas = transacaoService.findAll();

        Assertions.assertNotNull(transacaoModelSalvas);
        Assertions.assertFalse(transacaoModelSalvas.isEmpty());
    }

    @Test
    public void deveTestarFindById() {
        TransacaoModel transacaoModelSalva = transacaoService.save(requestMockDTO());
        Optional<TransacaoModel> transacaoModelParaBuscar = transacaoService.findById(transacaoModelSalva.getId());

        Assertions.assertNotNull(transacaoModelParaBuscar);
        Assertions.assertEquals(transacaoModelSalva.getId(), transacaoModelParaBuscar.get().getId());
    }

    @Test
    public void deveTestarDelete() {
        TransacaoModel transacaoModel = transacaoService.save(responseMockDTO());
        transacaoService.deleteById(transacaoModel.getId());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findById(transacaoModel.getId()));
    }

    @Test
    public void deveTestarUpdate() {
        TransacaoModel transacaoModel = transacaoService.save(requestMockDTO());
        TransacaoModel transacaoAtualizada = transacaoService.updateById(transacaoModel.getId(), requestMockDTO());

        Assertions.assertNotEquals(transacaoModel, transacaoAtualizada);
    }


    //Caso de falha
    @Test
    public void deveDarErroAoRealizarSave() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.save(requestMockNullDTO()));
    }

    @Test
    public void deveDarErroAoRealizarEstorno() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(UUID.randomUUID(), requestMockNullDTO()));
    }

    @Test
    public void deveDarErroAoRealizarFindAll() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findAll());
    }

    @Test
    public void deveDarErroAoRealizarFindById() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findById(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarDelete() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.deleteById(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarUpdate() {
        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.updateById(UUID.randomUUID(), requestMockNullDTO()));
    }

}
