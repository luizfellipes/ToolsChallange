package com.example.toolschallanger.services;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoModel = converterDtoEmEntity(transacaoRecordDTO);
        return transacaoRepository.save(transacaoModel);
    }

    public TransacaoModel estorno(UUID id) {
        TransacaoModel transacaoParaEstornar = findById(id).get();
        if (transacaoParaEstornar.getDescricaoModel().getStatus() == Status.AUTORIZADO) {
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
        } else {
            throw new IllegalArgumentException("Não foi possivel realizar um estorno, a transação esta cancelada !");
        }
    }

    public List<TransacaoModel> findAll() {
        return Optional.of(transacaoRepository.findAll())
                .orElseThrow(() -> new EntityNotFoundException("Sem transações registradas !"));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não existente !")));
    }

    public void deleteById(UUID id) {
        findById(id).ifPresent(transacaoModel -> transacaoRepository.deleteById(id));
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        Optional<TransacaoModel> transacaoExistente = findById(id);
        TransacaoModel transacaoModelNova = converterDtoEmEntity(transacaoRecordDTO);
        BeanUtils.copyProperties(transacaoExistente.get(), transacaoModelNova, "transacaoModel.id", "transacaoModel.descricaoModel.nsu", "transacaoModel.descricaoModel.codigoAutorizacao", "transacaoModel.descricaoModel.status");
        return transacaoRepository.save(transacaoModelNova);
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
    }

}
