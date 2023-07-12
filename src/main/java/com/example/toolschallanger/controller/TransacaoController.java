package com.example.toolschallanger.controller;

import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.TransacaoModel;
import com.example.toolschallanger.repositories.TransacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class TransacaoController {

    @Autowired
    TransacaoRepository transacaoRepository;
    @Autowired
    TransacaoModel transacaoModel;

    @PostMapping("/transacao")
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        BeanUtils.copyProperties(transacaoRecordDto, transacaoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoRepository.save(transacaoModel));
    }

    @GetMapping("/transacao")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao(){
        return ResponseEntity.status(HttpStatus.OK).body(transacaoRepository.findAll());
    }

}
