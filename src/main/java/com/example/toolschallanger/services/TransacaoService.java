package com.example.toolschallanger.services;

import com.example.toolschallanger.controller.TransacaoController;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    public TransacaoModel save(TransacaoRecordDTO transacaoRecordDTO) {
        return Stream.of(converterDtoEmEntity(transacaoRecordDTO))
                .map(transacaoModel -> {
                    transacaoModel.getDescricaoModel().geraValoresValidos();
                    transacaoRepository.save(transacaoModel);
                    log.info("Saved transaction with successfully.");
                    return transacaoModel;
                })
                .findFirst()
                .orElseThrow(TransacaoBadRequest::new);
    }

    public TransacaoModel estorno(UUID id) {
        return Stream.of(findById(id).get())
                .filter(transacaoModel -> Objects.equals(transacaoModel.getDescricaoModel().getStatus(), Status.AUTORIZADO))
                .map(transacaoModel -> {
                    transacaoModel.getDescricaoModel().setStatus(Status.CANCELADO);
                    transacaoRepository.save(transacaoModel);
                    log.info("Transaction reversed on the following ID: {}", id);
                    return transacaoModel;
                })
                .findFirst()
                .orElseThrow(TransacaoBadRequest::new);
    }

    public Page<TransacaoModel> findAll(Pageable pageable) {
        return Optional.of(transacaoRepository.findAll(pageable)
                        .map(transacoes -> {
                            transacoes.add(linkTo(methodOn(TransacaoController.class).getOne(transacoes.getId())).withSelfRel());
                            log.info("A search was carried out on the base.");
                            return transacoes;
                        }))
                .orElseThrow(() -> new TransacaoNaoEncontrada("Não há dados na base."));
    }

    public Optional<TransacaoModel> findById(UUID id) {
        return Optional.of(transacaoRepository.findById(id)
                        .map(transacao -> {
                            transacao.add(linkTo(methodOn(TransacaoController.class).getOne(id)).withSelfRel());
                            log.info("The following id was searched: {}", id);
                            return transacao;
                        }))
                .orElseThrow(() -> new TransacaoNaoEncontrada("ID não existente !"));
    }

    public void deleteById(UUID id) {
        findById(id).ifPresent(transacaoModel -> transacaoRepository.deleteById(id));
        log.info("Transaction deleted successfully.");
    }

    public TransacaoModel updateById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoExistente = findById(id).get();
        return Stream.of(converterDtoEmEntity(transacaoRecordDTO))
                .map(transacao -> {
                    copyProperties(transacao.getCartao(), transacaoExistente.getCartao());
                    copyProperties(transacao.getDescricaoModel(), transacaoExistente.getDescricaoModel());
                    copyProperties(transacao.getFormaPagamentoModel(), transacaoExistente.getFormaPagamentoModel());
                    transacaoRepository.save(transacaoExistente);
                    log.info("Updated transaction with successfully.");
                    return transacaoExistente;
                })
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel patchById(UUID id, TransacaoRecordDTO transacaoRecordDTO) {
        TransacaoModel transacaoPatching = findById(id).get();
        return Stream.of(converterDtoEmEntity(transacaoRecordDTO))
                .map(transacao -> {
                    transacaoPatching.setCartao(transacao.getCartao() != null ? transacao.getCartao() : transacaoPatching.getCartao());
                    copyProperties(transacao.getDescricaoModel(), transacaoPatching.getDescricaoModel());
                    copyProperties(transacao.getFormaPagamentoModel(), transacaoPatching.getFormaPagamentoModel());
                    transacaoRepository.save(transacaoPatching);
                    log.info("Patching transaction with successfully.");
                    return transacaoPatching;
                })
                .findFirst()
                .orElseThrow();
    }

    public TransacaoModel converterDtoEmEntity(TransacaoRecordDTO transacaoRecordDTO) {
        return new TransacaoModel(
                transacaoRecordDTO.cartao(),
                transacaoRecordDTO.descricaoDePagamento() != null ?
                        new DescricaoModel(transacaoRecordDTO.descricaoDePagamento().valor(), transacaoRecordDTO.descricaoDePagamento().dataHora(), transacaoRecordDTO.descricaoDePagamento().estabelecimento()) : null,
                transacaoRecordDTO.formaDePagamento() != null ?
                        new FormaPagamentoModel(transacaoRecordDTO.formaDePagamento().tipo(), transacaoRecordDTO.formaDePagamento().parcelas()) : null);
    }

}
