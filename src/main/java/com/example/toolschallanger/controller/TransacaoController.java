package com.example.toolschallanger.controller;

import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.TransacaoModel;
import com.example.toolschallanger.repositories.TransacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private TransacaoModel transacaoModel;


    @PostMapping("/transacao")
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        BeanUtils.copyProperties(transacaoRecordDto, transacaoModel);
        transacaoModel.setDescricaoModel(transacaoRecordDto.transacaoModel().getDescricaoModel());
        transacaoModel.setFormaPagamentoModel(transacaoRecordDto.transacaoModel().getFormaPagamentoModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoRepository.save(transacaoModel));
    }


    @GetMapping("/transacao")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao() {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoRepository.findAll());
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacao = transacaoRepository.findById(id);
        return transacao.<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }

    @DeleteMapping("deleteTransacao/{id}")
    public ResponseEntity<Object> deleteTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacaoModelOptional = transacaoRepository.findById(id);
        if (!transacaoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao não encontrada !");
        } else {
            transacaoRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Transacao deletada com sucesso !");
        }
    }



}
