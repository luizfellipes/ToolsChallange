package com.example.toolschallanger.controller;

import com.example.toolschallanger.Dtos.DescricaoRecordDto;
import com.example.toolschallanger.Dtos.FormaPagamentoRecordDto;
import com.example.toolschallanger.Dtos.ResultadoDTO;
import com.example.toolschallanger.Dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.DescricaoModel;
import com.example.toolschallanger.models.FormaPagamentoModel;
import com.example.toolschallanger.models.TransacaoModel;
import com.example.toolschallanger.repositories.DescricaoRepository;
import com.example.toolschallanger.repositories.FormaPagamentoRepository;
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
public class ApiController {

    @Autowired
    TransacaoRepository transacaoRepository;
    @Autowired
    DescricaoRepository descricaoRepository;
    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    TransacaoModel transacaoModel;
    @Autowired
    DescricaoModel descricaoModel;
    @Autowired
    FormaPagamentoModel formaPagamentoModel;
    @Autowired
    ResultadoDTO resultadoDTO;


    @PostMapping("/transacao")
    public ResponseEntity<ResultadoDTO> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto, DescricaoRecordDto descricaoRecordDto, FormaPagamentoRecordDto formaPagamentoDto) {
        BeanUtils.copyProperties(transacaoRecordDto, transacaoModel);
        BeanUtils.copyProperties(descricaoRecordDto, descricaoModel);
        BeanUtils.copyProperties(formaPagamentoDto, formaPagamentoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultadoDTO);
    }


    @GetMapping("/transacao")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao() {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoRepository.findAll());
    }

    @GetMapping("/transacao/{id}")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacao = transacaoRepository.findById(id);
        return transacao.<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }


}
