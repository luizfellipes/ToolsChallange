package com.example.toolschallanger.models.dtos;

import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.validation.constraints.NotNull;


public record FormaPagamentoRecordDTO(
        @NotNull(message = "É necessário ter o tipo de pagamento, por exemplo: AVISTA, PARCELADO_LOJA ou PARCELADO_EMISSOR.")
        FormaPagamento tipo,
        @NotNull(message = "É necessário ter a quantidade de parcelas!")
        Integer parcelas) {
}
