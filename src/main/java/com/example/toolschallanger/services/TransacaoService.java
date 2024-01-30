package com.example.toolschallanger.services;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;


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
        transacaoModel.getDescricaoModel().geraValoresAutomatico();
        transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel.getDescricaoModel().getValor());
        return transacaoRepository.save(transacaoModel);
    }

    public TransacaoModel estorno(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        findById(id);
        TransacaoModel transacaoParaEstornar = converterDtoEmEntity(transacaoRecordDTO);
        if (transacaoParaEstornar.getDescricaoModel().getStatus() != Status.CANCELADO) {
            transacaoParaEstornar.setId(id);
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
        } else {
            throw new RuntimeException("Não foi possivel realizar um estorno: A transação é diferente de AUTORIZADO !");
        }
    }

    public List<TransacaoModel> findAll() {
        if (transacaoRepository.findAll().isEmpty()) {
            throw new RuntimeException("Não foi possivel realizar uma busca na base: não existem transações registradas !");
        } else {
            return transacaoRepository.findAll();
        }
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Não foi encontrado nenhuma transação correspondente ao ID informado !")));
    }

    public Object deleteById(UUID id) {
        findById(id);
        transacaoRepository.deleteById(id);
        return ("O seguinte ID: ") + id + (" foi excluido com sucesso !");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        findById(id);
        TransacaoModel transacaoExistente = converterDtoEmEntity(transacaoRecordDTO);
        transacaoExistente.setId(id);
        transacaoExistente.getFormaPagamentoModel().validaParcela(transacaoExistente.getDescricaoModel().getValor());
        transacaoExistente.getDescricaoModel().geraValoresAutomatico();
        return transacaoRepository.save(transacaoExistente);
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
    }

}
