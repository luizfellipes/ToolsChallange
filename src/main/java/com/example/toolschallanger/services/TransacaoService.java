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
        if (transacaoParaEstornar.getDescricaoModel().getStatus() != Status.CANCELADO) {
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
        } else {
            throw new IllegalArgumentException("Não foi possivel realizar um estorno: A transação é diferente de AUTORIZADO !");
        }
    }

    public List<TransacaoModel> findAll() {
        return Optional.of(transacaoRepository.findAll())
                .filter(lista -> !lista.isEmpty())
                .orElseThrow(() -> new EntityNotFoundException("Sem transações registradas !"));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não existente !")));
    }

    public Object deleteById(UUID id) {
        findById(id).ifPresent(transacao -> transacaoRepository.deleteById(id));
        return Collections.singletonMap("Foi deletado o seguinte ID", id);
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        Optional<TransacaoModel> transacaoExistente = findById(id);
        TransacaoModel transacaoParaAtualizar = converterDtoEmEntity(transacaoRecordDTO);
        BeanUtils.copyProperties(transacaoExistente.get(), transacaoParaAtualizar, "id");
        return transacaoRepository.save(transacaoParaAtualizar);
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
    }


}
