package com.example.toolschallanger.entities;

import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TransacaoModelTest {


    @Test
    void deveVerificaObjetosParaIniciarValidaParcela() {
        DescricaoModel descricao = new DescricaoModel();
        FormaPagamentoModel formaPagamento = new FormaPagamentoModel();

        TransacaoModel transacao = new TransacaoModel(123456789L, descricao, formaPagamento);

        Assertions.assertDoesNotThrow(transacao::verificaFormaPagamentoParaIniciarValidaParcela);
    }

    @Test
    void deveDarErroAoVerificaObjetosParaIniciarValidaParcela() {
        TransacaoModel transacao = spy(new TransacaoModel(123456789L, null, null));

        transacao.verificaFormaPagamentoParaIniciarValidaParcela();

        verify(transacao, times(1)).verificaFormaPagamentoParaIniciarValidaParcela();
    }

}
