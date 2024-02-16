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
        return Optional.of(transacaoRepository.findAll())
                .filter(listaDeTransacoes -> !listaDeTransacoes.isEmpty())
                .orElseThrow(() -> new RuntimeException("Não foi possível buscar a transação na base, transação solicitada não existente!"));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não foi encontrado nenhuma transação correspondente ao ID informado !")));
    }

    public Object deleteById(UUID id) {
        findById(id);
        transacaoRepository.deleteById(id);
        return ("O seguinte id: ") + id + (" foi deletado com sucesso !");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoExistente = findById(id).get();
        TransacaoModel transacaoParaAtualizar = converterDtoEmEntity(transacaoRecordDTO);
        BeanUtils.copyProperties(transacaoExistente, transacaoParaAtualizar);
        transacaoParaAtualizar.geraValoresAutomatico();
        return transacaoRepository.save(transacaoParaAtualizar);
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoModel = new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
        transacaoModel.geraValoresAutomatico();
        transacaoModel.getFormaPagamentoModel().validaParcela(transacaoModel.getDescricaoModel().getValor());
        return transacaoModel;
    }


}
