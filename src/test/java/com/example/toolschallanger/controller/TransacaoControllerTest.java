package com.example.toolschallanger.controller;


import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.services.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.http.MediaType;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static com.example.toolschallanger.Mocks.MocksDTO.requestMockDTO;
import static com.example.toolschallanger.Mocks.MocksDTO.responseMockDTO;
import static com.example.toolschallanger.Mocks.MocksModel.requestMockModel;

import static com.example.toolschallanger.Mocks.MocksModel.responseMockModel;


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
    public void setUp() {
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
        when(transacaoService.save(any())).thenReturn(requestMockModel());

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(responseMockDTO())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.descricaoModel.status").value("AUTORIZADO"))
                .andReturn();
    }

    @Test
    public void deveRealizarUmEstorno() throws Exception {
        when(transacaoService.estorno(requestMockDTO())).thenReturn(responseMockModel());

        mockMvc.perform(post("/transacoes/estorno/" + responseMockModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestMockDTO())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricaoModel.status").value("CANCELADO"))
                .andReturn();
    }

    @Test
    public void deveRealizarUmUpdate() throws Exception {
        when(transacaoService.findById(any())).thenReturn(Optional.of(requestMockModel()));

        mockMvc.perform(put("/transacoes/" + requestMockModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseMockDTO())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetAll() throws Exception {
        when(transacaoService.findAll()).thenReturn(List.of(requestMockModel()));

        mockMvc.perform(get("/transacoes/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetOne() throws Exception {
        when(transacaoService.findById(any())).thenReturn(Optional.of(requestMockModel()));

        mockMvc.perform(get("/transacoes/" + requestMockModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseMockDTO())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoDelete() throws Exception {
        when(transacaoService.findById(any())).thenReturn(Optional.of(requestMockModel()));

        mockMvc.perform(delete("/transacoes/" + requestMockModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responseMockDTO())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    //deve testar caso de falha
    @Test
    public void deveDarErroNaCriacaoDeUmaNovaTransacao() throws Exception {
        when(transacaoService.save(any())).thenReturn(null);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deveDarErroAoRealizarUmaTransacaoUpdate() throws Exception {
        when(transacaoService.findById(any())).thenReturn(Optional.empty());

        mockMvc.perform(put("/transacoes/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Optional.empty())))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deveDarErroAoSolicitarTransacaoVaziasNoGetAll() throws Exception {
        when(transacaoService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/transacoes/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void deveTestarTransacaoGetOne_CasoDeFalha() throws Exception {
        mockMvc.perform(get("/transacoes/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void deveDarErroAoRealizarUmaTransacaoDelete() throws Exception {
        mockMvc.perform(delete("/transacoes/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
    }

}
