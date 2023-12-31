package com.example.toolschallanger.controllerTest;

import com.example.toolschallanger.controller.TransacaoController;
import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class TransacaoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private TransacaoService transacaoService;
    private TransacaoController transacaoController;

    @BeforeEach
    public void setUp(){
        this.transacaoService = mock(TransacaoService.class);
        this.transacaoController = new TransacaoController(transacaoService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        this.objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModule(new JavaTimeModule());
    }

    //deve testar caso de sucesso
    @Test
    public void deveCriarUmaNovaTransacao() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.now(), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);

        when(transacaoService.save(any())).thenReturn(transacaoModel);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.descricaoModel.status").value("AUTORIZADO"))
                .andReturn();
    }

    @Test
    public void deveRealizarUmEstorno() throws Exception {
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(
                new TransacaoModel(1065151L,
                        new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        mockMvc.perform(post("/estorno/" + transacaoRecordDto.transacaoModel().getId())
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
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(
                new TransacaoModel(UUID.randomUUID(),1065151L,
                        new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                        new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        when(transacaoService.findById(transacaoRecordDto.transacaoModel().getId())).thenReturn(Optional.of(transacaoRecordDto.transacaoModel()));
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
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
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(transacaoService.findById(transacaoModel.getId())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(get("/" + transacaoModel.getId()))
                .andExpect(status().isOk());
        Optional<TransacaoModel> transacaoFindById = transacaoService.findById(transacaoModel.getId());
        Assertions.assertEquals(Optional.of(transacaoModel), transacaoFindById);
    }

    @Test
    public void deveTestarTransacaoDelete() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        when(transacaoService.findById(transacaoModel.getId())).thenReturn(Optional.of(transacaoModel));
        mockMvc.perform(delete("/" + transacaoModel.getId()))
                .andExpect(status().isOk());
        Optional<TransacaoModel> transacaoFindById = transacaoService.findById(transacaoModel.getId());
        Assertions.assertEquals(Optional.of(transacaoModel), transacaoFindById);
    }

    //deve testar caso de falha
    @Test
    public void deveDarErroNaCriacaoDeUmaNovaTransacao() throws Exception {
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.CANCELADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deveDarErroAoRealizarUmaTransacaoUpdate() throws Exception {
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(new TransacaoModel(UUID.randomUUID(), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.010, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1)));
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto.transacaoModel())))
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
