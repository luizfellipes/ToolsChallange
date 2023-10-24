package com.example.toolschallanger.ControllerTest;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.FormaPagamento;
import com.example.toolschallanger.models.enuns.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void transacaoTestSave() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(post("/transacao")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void transacaoTestEstorno() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(500.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.ESTORNADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(post("/estorno/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(Status.ESTORNADO, transacaoRecordDto.transacaoModel().getDescricaoModel().getStatus());
    }

    @Test
    public void transacaoUpdate() throws Exception {
        TransacaoModel transacaoModel = new TransacaoModel(1065151L, new DescricaoModel(50.00, LocalDateTime.parse("2021-01-01T18:30:00"), "PetShop", 0000.1111, 00000.000, Status.AUTORIZADO), new FormaPagamentoModel(FormaPagamento.AVISTA, 1));
        transacaoModel.setId(UUID.fromString("e251f2ad-0e08-45b1-870e-8064baa1e3c0"));
        TransacaoRecordDto transacaoRecordDto = new TransacaoRecordDto(transacaoModel);
        mockMvc.perform(put("/" + transacaoRecordDto.transacaoModel().getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(transacaoRecordDto)))
                .andExpect(status().isOk());
    }

    @Test
    public void transacaoGetAll() throws Exception {
        mockMvc.perform(get("/transacao")).andExpect(status().isOk());
    }

    @Test
    public void transacaoGetOne() throws Exception {
        UUID id = UUID.fromString("e251f2ad-0e08-45b1-870e-8064baa1e3c0");
        mockMvc.perform(get("/transacao/" + id)).andExpect(status().isOk());
    }

    @Test
    public void transacaoDelete() throws Exception {
        UUID id = UUID.fromString("418b591e-3642-4c41-a6e2-0700191e15d7");
        mockMvc.perform(delete("/deleteTransacao/" + id)).andExpect(status().isOk());
    }

}
