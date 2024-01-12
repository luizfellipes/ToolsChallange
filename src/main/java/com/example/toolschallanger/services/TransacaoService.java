package com.example.toolschallanger.services;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


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
        if (!transacaoRepository.findAll().isEmpty()) {
            return transacaoRepository.findAll();
        } else {
            throw new RuntimeException("Não existe transações na base !");
        }
    }

    public Optional<TransacaoModel> findById(UUID id) {
        if (transacaoRepository.existsById(id)) {
            return transacaoRepository.findById(id);
        } else {
            throw new RuntimeException("O ID selecionado não existe !");
        }
    }

    public Object deleteById(UUID id) {
        if (transacaoRepository.findById(id).isEmpty()) {
            throw new RuntimeException("O ID selecionado não existe !");
        } else {
            transacaoRepository.deleteById(id);
            return true;
        }
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        if (transacaoRepository.existsById(id)) {
            TransacaoModel transacaoParaAtualizar = converterDtoEmEntity(transacaoRecordDTO);
            transacaoParaAtualizar.getFormaPagamentoModel().validaParcela(transacaoRecordDTO.descricaoDePagamento().valor());
            return transacaoRepository.save(transacaoParaAtualizar);
        } else {
            throw new RuntimeException("Id não encontrado !");
        }
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        DescricaoModel descricaoModel = new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento());
        FormaPagamentoModel formaPagamentoModel = new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas());
        return new TransacaoModel(transacaoRecordDTO.cartao(), descricaoModel, formaPagamentoModel);
    }

}
