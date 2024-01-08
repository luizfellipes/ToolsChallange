package com.example.toolschallanger.services;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoModel = converterDtoEmEntity(transacaoRecordDTO);
        transacaoModel.getDescricaoModel().geraValoresAutomatico();
        transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel.getDescricaoModel().getValor());
        return transacaoRepository.save(transacaoModel);
    }

    public TransacaoModel estorno(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoParaEstornar = converterDtoEmEntity(transacaoRecordDTO);
        if (transacaoParaEstornar.getDescricaoModel().getStatus() != Status.CANCELADO) {
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
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

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        DescricaoModel descricaoModel = new DescricaoModel(transacaoRecordDTO.descricaoRecordDTO().valor(), transacaoRecordDTO.descricaoRecordDTO().dataHora(), transacaoRecordDTO.descricaoRecordDTO().estabelecimento());
        FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel(transacaoRecordDTO.formaPagamentoRecordDTO().tipo(), transacaoRecordDTO.formaPagamentoRecordDTO().parcelas());
        return new TransacaoModel(transacaoRecordDTO.cartao(), descricaoModel, formaPagamentoModel);
    }
}
