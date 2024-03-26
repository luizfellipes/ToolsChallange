package com.example.toolschallanger.services;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.exceptions.TransacaoNaoEncontrada;

import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;

import com.example.toolschallanger.repositories.TransacaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.example.toolschallanger.mocks.MocksDTO.*;
import static com.example.toolschallanger.mocks.MocksDTO.requestMockDTO;

import static com.example.toolschallanger.mocks.MocksModel.requestMockModel;
import static com.example.toolschallanger.mocks.MocksModel.responseMockModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TransacaoServiceTest {

    private TransacaoService transacaoService;
    private TransacaoRepository transacaoRepository;


    @BeforeEach
    void setUp() {
        this.transacaoRepository = mock(TransacaoRepository.class);
        this.transacaoService = new TransacaoService(transacaoRepository);
    }

    //Caso de sucesso
    @Test
    void deveTestarSaveComSucesso() {
        TransacaoModel mockModel = responseMockModel();
        when(transacaoRepository.save(any())).thenReturn(mockModel);

        TransacaoModel savedTransacao = transacaoService.save(requestMockDTO());

        Assertions.assertEquals(mockModel, savedTransacao);
    }

    @Test
    void deveTestarEstorno() {
        TransacaoModel transacaoModel = requestMockModel();
        when(transacaoRepository.save(transacaoModel)).thenReturn(transacaoModel);
        when(transacaoRepository.findById(transacaoModel.getId())).thenReturn(Optional.of(transacaoModel));
        when(transacaoService.estorno(transacaoModel.getId())).thenReturn(transacaoModel);

        Assertions.assertEquals(Status.CANCELADO, transacaoModel.getDescricaoModel().getStatus());
    }

    @Test
    void deveTestarFindAllComSucesso() {
        Page<TransacaoModel> mockPage = new PageImpl<>(Collections.singletonList(responseMockModel()));
        when(transacaoRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<TransacaoModel> transacoes = transacaoService.findAll(Pageable.unpaged());

        Assertions.assertFalse(transacoes.isEmpty());
    }

    @Test
    void deveTestarFindByIdComSucesso() {
        TransacaoModel mockModel = responseMockModel();
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(mockModel));

        Optional<TransacaoModel> transacao = transacaoService.findById(UUID.randomUUID());

        Assertions.assertTrue(transacao.isPresent());
    }

    @Test
    void deveTestarDeleteByIdComSucesso() {
        TransacaoModel mockModel = responseMockModel();
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(mockModel));

        Executable transacaoApagada = () -> transacaoService.deleteById(UUID.randomUUID());

        Assertions.assertDoesNotThrow(transacaoApagada);
    }

    @Test
    void deveTestarUpdateByIdComSucesso() {
        TransacaoModel mockModel = responseMockModel();
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(mockModel));
        when(transacaoRepository.save(any())).thenReturn(mockModel);

        TransacaoModel updatedTransacao = transacaoService.updateById(UUID.randomUUID(), requestMockDTO());

        Assertions.assertEquals(mockModel, updatedTransacao);
    }

    @Test
    void deveTestarPatchByIdComSucesso() {
        TransacaoModel mockModel = responseMockModel();
        when(transacaoRepository.findById(any())).thenReturn(Optional.of(mockModel));
        when(transacaoRepository.save(any())).thenReturn(mockModel);

        TransacaoModel patchedTransacao = transacaoService.patchById(UUID.randomUUID(), requestMockDTO());

        Assertions.assertEquals(mockModel, patchedTransacao);
    }

    @Test
    void deveTestarConverterDtoEmEntity() {
        TransacaoModel transacaoModel = transacaoService.converterDtoEmEntity(requestMockDTO());

        Assertions.assertNotNull(transacaoModel);
    }


    //Caso de falha
    @Test
    void deveDarErroAoSalvarTransacao() {
        when(transacaoRepository.save(any())).thenThrow(new TransacaoBadRequest());

        Executable save = () -> transacaoService.save(requestMockDTO());

        Assertions.assertThrows(TransacaoBadRequest.class, save);
    }


    @Test
    void deveDarErroAoEstornarTransacao() {
        when(transacaoRepository.findById(any())).thenThrow(new TransacaoBadRequest());

        Executable estorno = () -> transacaoService.estorno(UUID.randomUUID());

        Assertions.assertThrows(TransacaoBadRequest.class, estorno);
    }


    @Test
    void deveDarErroAoBuscarTodasAsTransacoes() {
        when(transacaoRepository.findAll(any(Pageable.class))).thenThrow(new TransacaoNaoEncontrada());

        Executable findAll = () -> transacaoService.findAll(Pageable.unpaged());

        Assertions.assertThrows(TransacaoNaoEncontrada.class, findAll);
    }

    @Test
    void deveDarErroAoRealizarFindById() {
        when(transacaoRepository.findById(any())).thenThrow(new TransacaoNaoEncontrada());

        Executable findById = () -> transacaoService.findById(UUID.randomUUID());

        Assertions.assertThrows(TransacaoNaoEncontrada.class, findById);
    }

    @Test
    void deveDarErroAoRealizarDelete() {
        doThrow(new TransacaoBadRequest()).when(transacaoRepository).deleteById(any(UUID.class));

        Executable delete = () -> transacaoService.deleteById(UUID.randomUUID());

        Assertions.assertThrows(TransacaoNaoEncontrada.class, delete);
    }

    @Test
    void deveDarErroAoRealizarUpdate() {
        when(transacaoRepository.save(any())).thenThrow(new TransacaoNaoEncontrada());

        Executable update = () -> transacaoService.updateById(UUID.randomUUID(), requestMockNullDTO());

        Assertions.assertThrows(TransacaoNaoEncontrada.class, update);
    }

    @Test
    void deveDarErroAoAplicarPatchEmTransacaoPorId() {
        when(transacaoRepository.findById(any())).thenThrow(new TransacaoNaoEncontrada());

        Executable patchById = () -> transacaoService.patchById(UUID.randomUUID(), requestMockDTO());

        Assertions.assertThrows(TransacaoNaoEncontrada.class, patchById);
    }

}
