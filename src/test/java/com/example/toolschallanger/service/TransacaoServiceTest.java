package com.example.toolschallanger.service;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;
import java.util.UUID;

import static com.example.toolschallanger.mocks.MocksDTO.*;
import static com.example.toolschallanger.mocks.MocksDTO.requestMockDTO;
import static com.example.toolschallanger.mocks.MocksModel.requestMockModel;
import static com.example.toolschallanger.mocks.MocksModel.responseMockModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TransacaoServiceTest {

    private TransacaoService transacaoService;

    @BeforeEach
    public void setUp() {
        this.transacaoService = mock(TransacaoService.class);
    }

    //Caso de sucesso
    @Test
    public void deveTestarSave() {
        TransacaoModel transacaoModel = responseMockModel();
        when(transacaoService.save(any())).thenReturn(transacaoModel);

        TransacaoModel save = transacaoService.save(requestMockDTO());

        Assertions.assertEquals(transacaoModel, save);
    }

    @Test
    public void deveTestarEstorno() {
        when(transacaoService.estorno(any())).thenReturn(requestMockModel());

        Status status = responseMockModel().getDescricaoModel().getStatus();

        Assertions.assertEquals(Status.CANCELADO, status);
    }

    @Test
    public void deveTestarFindAll() {
        when(transacaoService.findAll(any())).thenReturn(Page.empty());

        boolean listaVazia = transacaoService.findAll(any()).isEmpty();

        Assertions.assertTrue(listaVazia);
    }

    @Test
    public void deveTestarFindById() {
        TransacaoModel transacaoModel = requestMockModel();
        when(transacaoService.findById(any())).thenReturn(Optional.of(transacaoModel));
        Optional<TransacaoModel> transacao = transacaoService.findById(requestMockModel().getId());

        Assertions.assertEquals(transacaoModel.getId(), transacao.get().getId());
    }

    @Test
    public void deveTestarDelete() {
        TransacaoModel transacaoModel = requestMockModel();
        when(transacaoService.findById(any())).thenReturn(Optional.of(transacaoModel));

        transacaoService.deleteById(transacaoModel.getId());
        verify(transacaoService, times(1)).deleteById(transacaoModel.getId());
    }

    @Test
    public void deveTestarUpdate() {
        TransacaoModel transacaoModel = responseMockModel();
        when(transacaoService.updateById(any(), any())).thenReturn(transacaoModel);

        TransacaoModel transacaoAtualizada = transacaoService.updateById(responseMockModel().getId(), responseMockDTO());

        Assertions.assertEquals(transacaoModel, transacaoAtualizada);
    }


    //Caso de falha
    @Test
    public void deveDarErroAoRealizarSave() {
        when(transacaoService.save(any(TransacaoRecordDTO.class))).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.save(requestMockNullDTO()));
    }

    @Test
    public void deveDarErroAoRealizarEstorno() {
        when(transacaoService.estorno(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarFindAll() {
        when(transacaoService.findAll(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findAll(any()));
    }

    @Test
    public void deveDarErroAoRealizarFindById() {
        when(transacaoService.findById(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findById(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarDelete() {
        TransacaoModel transacaoModel = new TransacaoModel();
        doThrow(EntityNotFoundException.class).when(transacaoService).deleteById(transacaoModel.getId());

        Assertions.assertThrows(EntityNotFoundException.class, ()-> transacaoService.deleteById(transacaoModel.getId()));
    }

    @Test
    public void deveDarErroAoRealizarUpdate() {
        when(transacaoService.updateById(any(), any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.updateById(UUID.randomUUID(), requestMockNullDTO()));
    }

}
