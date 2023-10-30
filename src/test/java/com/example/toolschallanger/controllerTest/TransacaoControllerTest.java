package com.example.toolschallanger.controllerTest;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;
import com.example.toolschallanger.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TransacaoService service;


    //deve testar caso de sucesso
    @Test
    public void deveTestarTransacao() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(transacaoModel, transacaoRecordDto.transacaoModel());
    }

    @Test
    public void deveTestarTransacaoTestEstorno() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.CANCELADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(post("/estorno/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(Status.CANCELADO, transacaoRecordDto.transacaoModel().getDescricaoModel().getStatus());
    }


    @Test
    public void deveTestarTransacaoUpdate() throws Exception {
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.CANCELADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isOk());
    }


    @Test
    public void deveTestarTransacaoGetAll() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(service.findAll()).thenReturn(List.of(transacaoModel));
        List<TransacaoModel> transacaoModelList = service.findAll();
        Assertions.assertNotNull(transacaoModelList);
    }

    @Test
    public void deveTestarTransacaoGetOne() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(service.findById(transacaoModel.getId())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(get("/" + transacaoModel.getId())).andExpect(status().isOk());
    }

    @Test
    public void deveTestarTransacaoDelete() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(service.findById(transacaoModel.getId())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(delete("/" + transacaoModel.getId())).andExpect(status().isOk());
    }

    //deve testar caso de falha
    @Test
    public void deveTestarTransacao_CasoDeFalaha() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TransacaoRecordDto(transacaoModel))))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveTestarTransacaoUpdate_CasoDeFalaha() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto.transacaoModel())))
                .andExpect(status().isNotFound());
        Assertions.assertNotNull(transacaoRecordDto);
        Assertions.assertEquals(transacaoModel, transacaoRecordDto.transacaoModel());
    }


}