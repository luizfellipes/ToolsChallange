package com.example.toolschallanger.services;

import com.example.toolschallanger.exceptions.TransacaoBadRequest;
import com.example.toolschallanger.exceptions.TransacaoNaoEncontrada;
import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.models.entities.DescricaoModel;
import com.example.toolschallanger.models.entities.FormaPagamentoModel;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.repositories.TransacaoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static com.example.toolschallanger.config.CopyPropertiesConfig.myCopyProperties;


@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        return Stream.of(transacaoRecordDTO)
                .map(this::converterDtoEmEntity)
                .peek(transacaoModel -> transacaoModel.getDescricaoModel().geraValoresValidos())
                .map(transacaoRepository::save)
                .peek(l -> log.info("Saved transaction."))
                .findFirst()
                .orElseThrow(TransacaoBadRequest::new);
    }

    public TransacaoModel estorno(UUID id) {
        return Stream.of(findById(id))
                .peek(l -> log.info("Transaction reversed on the following ID:" + id))
                .filter(transacaoModel -> transacaoModel.get().getDescricaoModel().getStatus() == Status.AUTORIZADO)
                .peek(transacaoModel -> transacaoModel.get().getDescricaoModel().setStatus(Status.CANCELADO))
                .map(transacaoModel -> transacaoRepository.save(findById(id).get()))
                .findFirst()
                .orElseThrow(TransacaoBadRequest::new);
    }

    public Page<TransacaoModel> findAll(Pageable pageable) {
        return Optional.of(transacaoRepository.findAll(pageable))
                .stream()
                .peek(l -> log.info("A search was carried out on the base."))
                .findFirst()
                .orElseThrow(() -> new TransacaoNaoEncontrada("Não há dados na base."));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.ofNullable(transacaoRepository.findById(id)
                .stream()
                .peek(l -> log.info("The following id was searched: " + id))
                .findFirst()
                .orElseThrow(() -> new TransacaoNaoEncontrada("ID não existente !")));
    }

    public void deleteById(UUID id) {
        findById(id).ifPresent(transacaoModel -> transacaoRepository.deleteById(id));
        log.info("Transaction deleted successfully.");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoUpdate = converterDtoEmEntity(transacaoRecordDTO);
        return Stream.of(findById(id).get())
                .peek(transacao -> myCopyProperties(transacaoUpdate.getDescricaoModel(), transacao.getDescricaoModel()))
                .peek(transacao -> myCopyProperties(transacaoUpdate.getFormaPagamentoModel(), transacao.getFormaPagamentoModel()))
                .peek(transacao -> BeanUtils.copyProperties(transacaoUpdate, transacao, "id", "descricaoModel", "formaPagamentoModel"))
                .map(transacaoRepository::save)
                .peek(l -> log.info("Merged transaction with successfully."))
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel patchById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoParaPatching = converterDtoEmEntity(transacaoRecordDTO);
        return Stream.of(findById(id).get())
                .peek(transacao -> myCopyProperties(transacaoParaPatching.getDescricaoModel(), transacao.getDescricaoModel()))
                .peek(transacao -> myCopyProperties(transacaoParaPatching.getFormaPagamentoModel(), transacao.getFormaPagamentoModel()))
                .peek(transacao -> BeanUtils.copyProperties(transacaoParaPatching, transacao, "id", "descricaoModel", "formaPagamentoModel"))
                .map(transacaoRepository::save)
                .peek(l -> log.info("Merged transaction with successfully."))
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()),
                new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()));
    }

}
