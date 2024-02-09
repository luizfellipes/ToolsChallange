package com.example.toolschallanger.service;

import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;
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

        Assertions.assertEquals(transacaoModel, transacaoService.save(requestMockDTO()));
    }

    @Test
    public void deveTestarEstorno() {
        when(transacaoService.estorno(any(), any())).thenReturn(requestMockModel());

        Assertions.assertEquals(Status.CANCELADO, responseMockModel().getDescricaoModel().getStatus());
    }

    @Test
    public void deveTestarFindAll() {
        when(transacaoService.findAll()).thenReturn(List.of(requestMockModel()));

        Assertions.assertFalse(transacaoService.findAll().isEmpty());
    }

    @Test
    public void deveTestarFindById() {
        when(transacaoService.findById(any())).thenReturn(Optional.of(requestMockModel()));
        Optional<TransacaoModel> transacao = transacaoService.findById(requestMockModel().getId());

        Assertions.assertEquals(requestMockModel().getId() ,transacao.get().getId());
    }

    @Test
    public void deveTestarDelete() {
        when(transacaoService.deleteById(any())).thenReturn(null);

        Assertions.assertNull(transacaoService.deleteById(requestMockModel().getId()));
    }

    @Test
    public void deveTestarUpdate() {
        TransacaoModel transacaoModel = responseMockModel();
        when(transacaoService.updateById(any(), any())).thenReturn(transacaoModel);

        Assertions.assertEquals(transacaoModel, transacaoService.updateById(responseMockModel().getId(), responseMockDTO()));
    }


    //Caso de falha
    @Test
    public void deveDarErroAoRealizarSave() {
        when(transacaoService.save(requestMockNullDTO())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.save(requestMockNullDTO()));
    }

    @Test
    public void deveDarErroAoRealizarEstorno() {
        when(transacaoService.estorno(any(), any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.estorno(UUID.randomUUID(), requestMockNullDTO()));
    }

    @Test
    public void deveDarErroAoRealizarFindAll() {
        when(transacaoService.findAll()).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findAll());
    }

    @Test
    public void deveDarErroAoRealizarFindById() {
        when(transacaoService.findById(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.findById(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarDelete() {
        when(transacaoService.deleteById(any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.deleteById(UUID.randomUUID()));
    }

    @Test
    public void deveDarErroAoRealizarUpdate() {
        when(transacaoService.updateById(any(), any())).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> transacaoService.updateById(UUID.randomUUID(), requestMockNullDTO()));
    }

}
