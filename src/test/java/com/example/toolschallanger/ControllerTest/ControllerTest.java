package com.example.toolschallanger.ControllerTest;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void deveTestarTransacao() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isCreated());
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
        TransacaoModel transacaoModel = new TransacaoModel(UUID.fromString("917f9d2b-47ce-4adf-8e9d-452d3a5887ff"), 1065151L,
                new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO),
                new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isOk());
    }


    @Test
    public void deveTestarTransacaoGetAll() throws Exception {
        mockMvc.perform(get("/transacao")).andExpect(status().isOk());
    }

    @Test
    public void deveTestarTransacaoGetOne() throws Exception {
        UUID id = UUID.fromString("917f9d2b-47ce-4adf-8e9d-452d3a5887ff");
        mockMvc.perform(get("/transacao/" + id)).andExpect(status().isOk());
    }

    @Test
    public void deveTestarTransacaoDelete() throws Exception {
        UUID id = UUID.fromString("418b591e-3642-4c41-a6e2-0700191e15d7");
        mockMvc.perform(delete("/deleteTransacao/" + id)).andExpect(status().isOk());
    }

}
