package com.example.toolschallanger.services;


import com.example.toolschallanger.exceptions.RequestExceptionNotFound;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;
import com.example.toolschallanger.exceptions.RequestExceptionBadRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LogManager.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoModel = converterDtoEmEntity(transacaoRecordDTO);
        transacaoRepository.save(transacaoModel);
        log.info("Saved transaction.");
        return transacaoModel;
    }

    public TransacaoModel estorno(UUID id) {
        TransacaoModel transacaoParaEstornar = findById(id).get();
        if (transacaoParaEstornar.getDescricaoModel().getStatus() == Status.AUTORIZADO) {
            transacaoParaEstornar.getDescricaoModel().setStatus(Status.CANCELADO);
            transacaoRepository.save(transacaoParaEstornar);
            log.info("Transaction reversed on the following ID:" + id);
            return transacaoParaEstornar;
        } else {
            log.error("Unable to make a transaction on the following ID:" + id);
            throw new RequestExceptionBadRequest("Estorno não permitido: Transação não autorizada!");
        }
    }

    public List<TransacaoModel> findAll() {
        List<TransacaoModel> findAll = Optional.of(transacaoRepository.findAll())
                .orElseThrow(() -> new RequestExceptionNotFound("Não há dados na base."));
        log.info("A search was carried out on the base.");
        return findAll;
    }

    public Optional<TransacaoModel> findById(UUID id) {
        Optional<TransacaoModel> findByid = Optional.ofNullable(transacaoRepository.findById(id)
                .orElseThrow(() -> new RequestExceptionNotFound("ID não existente !")));
        log.info("The following id was searched: " + id);
        return findByid;
    }

    public void deleteById(UUID id) {
        findById(id).ifPresent(transacaoModel -> transacaoRepository.deleteById(id));
        log.info("Transaction deleted successfully.");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoExistente = findById(id).get();
        TransacaoModel transacaoModelNova = converterDtoEmEntity(transacaoRecordDTO);
        BeanUtils.copyProperties(transacaoModelNova, transacaoExistente, "id");
        transacaoRepository.save(transacaoExistente);
        log.info("Updated transaction.");
        return transacaoExistente;
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoModel = new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
        log.info("Transaction transformed from DTO to Entity. " + transacaoModel);
        return transacaoModel;
    }

}
