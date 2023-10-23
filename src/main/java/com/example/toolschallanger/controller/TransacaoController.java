package com.example.toolschallanger.controller;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.services.TransacaoService;
import com.example.toolschallanger.models.entities.TransacaoModel;
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
    TransacaoService transacaoService;

    @PostMapping("/transacao")
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
       // transacaoService.setAtributosDtoModel(transacaoRecordDto);
       // BeanUtils.copyProperties(transacaoRecordDto, transacaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.save(transacaoRecordDto.transacaoModel()));
    }

    @GetMapping("/transacao")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao() {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll());
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacao = transacaoService.findById(id);
        return transacao.<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }

    @DeleteMapping("/deleteTransacao/{id}")
    public ResponseEntity<Object> deleteTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacaoModelOptional = transacaoService.findById(id);
        if (transacaoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transacao não encontrada !");
        } else {
            transacaoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Transacao deletada com sucesso !");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTransacao(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        Optional<TransacaoModel> transacaoModelOptional = transacaoService.findById(id);
        if (transacaoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !");
        }
        BeanUtils.copyProperties(transacaoRecordDto, transacaoRecordDto.transacaoModel());
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.save(transacaoRecordDto.transacaoModel()));
    }


}
