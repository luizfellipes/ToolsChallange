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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static com.example.toolschallanger.config.CopyPropertiesConfig.copyProperties;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        return Stream.of(converterDtoEmEntity(transacaoRecordDTO))
                .peek(transacaoModel -> transacaoModel.getDescricaoModel().geraValoresValidos())
                .map(transacaoRepository::save)
                .peek(l -> log.info("Saved transaction with successfully."))
                .findFirst()
                .orElseThrow(TransacaoBadRequest::new);
    }

    public TransacaoModel estorno(UUID id) {
        return Stream.of(findById(id).get())
                .peek(l -> log.info("Transaction reversed on the following ID:" + id))
                .filter(transacaoModel -> transacaoModel.getDescricaoModel().getStatus() == Status.AUTORIZADO)
                .peek(transacaoModel -> transacaoModel.getDescricaoModel().setStatus(Status.CANCELADO))
                .map(transacaoRepository::save)
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
        TransacaoModel transacaoExistente = findById(id).get();
        return Stream.of(transacaoRecordDTO)
                .map(this::converterDtoEmEntity)
                .peek(transacao -> copyProperties(transacao.getCartao(), transacaoExistente.getCartao()))
                .peek(transacao -> copyProperties(transacao.getDescricaoModel(), transacaoExistente.getDescricaoModel()))
                .peek(transacao -> copyProperties(transacao.getFormaPagamentoModel(), transacaoExistente.getFormaPagamentoModel()))
                .map(transacao -> transacaoRepository.save(transacaoExistente))
                .peek(l -> log.info("Updated transaction with successfully."))
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel patchById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoPatching = findById(id).get();
        return Stream.of(transacaoRecordDTO)
                .map(this::converterDtoEmEntity)
                .peek(transacao -> transacaoPatching.setCartao(transacao.getCartao() != null ? transacao.getCartao() : transacaoPatching.getCartao()))
                .peek(transacao -> copyProperties(transacao.getDescricaoModel(), transacaoPatching.getDescricaoModel()))
                .peek(transacao -> copyProperties(transacao.getFormaPagamentoModel(), transacaoPatching.getFormaPagamentoModel()))
                .map(transacao -> transacaoRepository.save(transacaoPatching))
                .peek(l -> log.info("Patching transaction with successfully."))
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(transacaoRecordDTO.cartao(),
                transacaoRecordDTO.descricaoDePagamento() != null ?
                        new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()) : null,
                transacaoRecordDTO.formaDePagamento() != null ?
                        new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()) : null);
    }

}
