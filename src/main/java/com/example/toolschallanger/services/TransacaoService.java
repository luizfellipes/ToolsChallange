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
        TransacaoModel transacaoParaEstornar = converterDtoEmEntity(transacaoRecordDTO);
        if (findById(id).isPresent() && transacaoParaEstornar.getDescricaoModel().getStatus() != Status.CANCELADO) {
            transacaoParaEstornar.setId(id);
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            return transacaoRepository.save(transacaoParaEstornar);
        } else {
            throw new RuntimeException("Não foi possivel realizar um estorno: A transação é diferente de AUTORIZADO !");
        }
    }

    public List<TransacaoModel> findAll() {
        if (!transacaoRepository.findAll().isEmpty()) {
            return transacaoRepository.findAll();
        } else {
            throw new RuntimeException("Não foi possivel realizar uma busca na base: não existem transações registradas !");
        }
    }

    public Optional<TransacaoModel> findById(UUID id) {
        if (transacaoRepository.existsById(id)) {
            return transacaoRepository.findById(id);
        } else {
            throw new RuntimeException("Não existe transação com o ID informado !");
        }
    }

    public Object deleteById(UUID id) {
        findById(id).orElseThrow(() -> new RuntimeException("Não foi possivel realizar a exclusão pois ID selecionado já foi excluido ou não existe !"));
        transacaoRepository.deleteById(id);
        return ("O seguinte ID: ") + id + (" foi excluido com sucesso !");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        findById(id).orElseThrow(() -> new RuntimeException("Não foi possível realizar um update: Id não encontrado !"));
        Optional<TransacaoModel> transacaoExistente = findById(id);
        transacaoExistente.get().getFormaPagamentoModel().validaParcela(transacaoExistente.get().getDescricaoModel().getValor());
        return transacaoRepository.save(
                new TransacaoModel(id, transacaoRecordDTO.cartao(),
                        new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento(),
                                transacaoExistente.get().getDescricaoModel().getNsu(), transacaoExistente.get().getDescricaoModel().getCodigoAutorizacao(), transacaoExistente.get().getDescricaoModel().getStatus()),
                        new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas())));
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));

    }

}
