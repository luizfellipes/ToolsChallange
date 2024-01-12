package com.example.toolschallanger.models.dtos;

import com.example.toolschallanger.models.enuns.FormaPagamento;
import jakarta.validation.constraints.NotNull;


public record FormaPagamentoRecordDTO(
        @NotNull(message = "O tipo de pagamento não pode ser nulo. Escolha entre: AVISTA, PARCELADO_LOJA ou PARCELADO_EMISSOR.")
        FormaPagamento tipo,
        @NotNull(message = "A quantidade de parcelas deve ser inserida sendo, 1 para avista e maior que 1 para parcelado")
        Integer parcelas) {
}
