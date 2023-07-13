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

    private final TransacaoRepository transacaoRepository;

    public TransacaoController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping("/transacao")
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        TransacaoModel transacaoModel = new TransacaoModel(transacaoRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoRepository.save(transacaoModel));
    }


    @GetMapping("/transacao")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao(){
        return ResponseEntity.status(HttpStatus.OK).body(transacaoRepository.findAll());
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Object> getOneTransacao(@PathVariable (value = "id") UUID id){
        Optional<TransacaoModel> transacao = transacaoRepository.findById(id);
        return transacao.<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }




}
