package com.example.toolschallanger.services;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
        if (transacaoParaEstornar.getDescricaoModel().getStatus() != Status.CANCELADO) {
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
        } else {
            throw new RuntimeException("Não foi possivel realizar um estorno: A transação é diferente de AUTORIZADO !");
        }
    }

    public List<TransacaoModel> findAll() {
        return transacaoRepository.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toList(),
                        lista -> {
                            if (lista.isEmpty()) {
                                throw new RuntimeException("Lista de transações vazia");
                            }
                            return lista;
                        }));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID não existente !")));
    }

    public Object deleteById(UUID id) {
        findById(id);
        transacaoRepository.deleteById(id);
        return Collections.singleton("Foi deletado o seguinte ID" + id);
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        Optional<TransacaoModel> transacaoExistente = findById(id);
        TransacaoModel transacaoParaAtualizar = converterDtoEmEntity(transacaoRecordDTO);
        BeanUtils.copyProperties(transacaoExistente, transacaoParaAtualizar, "id");
        return transacaoRepository.save(transacaoParaAtualizar);
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
    }


}
