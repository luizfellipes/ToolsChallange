package com.example.toolschallanger.services;

import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    public TransacaoModel save(TransacaoModel transacaoModel) {
        transacaoModel.getDescricaoModel().geraValoresAutomatico();
        transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel);
        return transacaoRepository.save(transacaoModel);
    }

    public TransacaoModel estorno(TransacaoModel transacaoModel) {
        if (transacaoModel.getDescricaoModel().getStatus() == Status.AUTORIZADO) {
            transacaoModel.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoModel);
        } else {
            throw new RuntimeException("Não é possivel estornar uma transação diferente de AUTORIZADO !");
        }
    }

    public List<TransacaoModel> findAll() {
        return transacaoRepository.findAll();
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return transacaoRepository.findById(id);
    }

    public void deleteById(UUID id) {
        transacaoRepository.deleteById(id);
    }

}
