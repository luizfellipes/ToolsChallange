package com.example.toolschallanger.controllerTest;

import com.example.toolschallanger.models.dtos.DescricaoRecordDTO;
import com.example.toolschallanger.models.dtos.FormaPagamentoRecordDTO;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.example.toolschallanger.models.enuns.FormaPagamento.AVISTA;
import static com.example.toolschallanger.models.enuns.FormaPagamento.PARCELADO_EMISSOR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private TransacaoService transacaoService;


    //deve testar caso de sucesso
    @Test
    public void deveCriarUmaNovaTransacao() throws Exception {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.descricaoModel.status").value("AUTORIZADO"))
                .andReturn();
    }

    @Test
    public void deveRealizarUmEstorno() throws Exception {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoModel(AVISTA, 1));
        mockMvc.perform(post("/estorno/" + transacaoModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.descricaoModel.status").value("CANCELADO"))
                .andReturn();
    }

    @Test
    public void deveRealizarUmUpdate() throws Exception {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoModel(AVISTA, 1));
        when(transacaoService.findById(any())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(put("/" + transacaoModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetAll() throws Exception {
        TransacaoModel transacaoModel =
                new TransacaoModel(UUID.randomUUID(), 1065151L,
                        new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(transacaoService.findAll()).thenReturn(List.of(transacaoModel));
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetOne() throws Exception {
        TransacaoRecordDTO transacaoRecordDto = new TransacaoRecordDTO(1065151L,
                new DescricaoRecordDTO(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoRecordDTO(FormaPagamento.AVISTA, 1));
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop"),
                new FormaPagamentoModel(AVISTA, 1));
        when(transacaoService.findById(any())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(get("/" + transacaoModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoDelete() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(transacaoService.findById(any())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(delete("/" + transacaoModel.getId()))
                .andExpect(status().isOk());
        Optional<TransacaoModel> transacaoFindById = transacaoService.findById(transacaoModel.getId());
        Assertions.assertEquals(Optional.of(transacaoModel), transacaoFindById);
    }

    //deve testar caso de falha
    @Test
    public void deveDarErroNaCriacaoDeUmaNovaTransacao() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel();
        mockMvc.perform(post("/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoModel)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deveDarErroAoRealizarUmaTransacaoUpdate() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L, new DescricaoModel(90.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(PARCELADO_EMISSOR, 1));
        mockMvc.perform(put("/" + transacaoModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoModel)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deveDarErroAoSolicitarTransacaoVaziasNoGetAll() throws Exception {
        when(transacaoService.findAll()).thenReturn(List.of());
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetOne_CasoDeFalha() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        mockMvc.perform(get("/" + transacaoModel.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deveDarErroAoRealizarUmaTransacaoDelete() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        mockMvc.perform(delete("/" + transacaoModel.getId()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
