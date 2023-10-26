package com.example.toolschallanger.controller;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.services.TransacaoService;
import com.example.toolschallanger.models.entities.TransacaoModel;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/transacao")
@CrossOrigin(origins = "*")
public class TransacaoController {

    @Autowired
    TransacaoService transacaoService;


    @PostMapping("/")
    @Operation(summary = "Criar", description = "Salvar novas transações", tags = "Transações")
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.save(transacaoRecordDto.transacaoModel()));
    }

    @PostMapping("/estorno/{id}")
    @Operation(summary = "Estorno", description = "Estorna transações", tags = "Transações")
    public ResponseEntity<TransacaoModel> estorno(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.estorno(transacaoRecordDto.transacaoModel()));
    }

    @GetMapping("/")
    @Operation(summary = "Lista as transações", description = "Busca todas as transações", tags = "Transações")
    public ResponseEntity<List<TransacaoModel>> getAlltransacao() {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista as transações por ID", description = "Busca as transações por ID", tags = "Transações")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacao = transacaoService.findById(id);
        return transacao.<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta por transações por ID", description = "Deleta as transações por ID", tags = "Transações")
    public ResponseEntity<Object> deleteTransacao(@PathVariable(value = "id") UUID id) {
        Optional<TransacaoModel> transacaoModelOptional = transacaoService.findById(id);
        if (transacaoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !");
        } else {
            transacaoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Transação deletada com sucesso !");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza transações por ID", description = "Atualiza as transações por ID", tags = "Transações")
    public ResponseEntity<Object> updateTransacao(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        Optional<TransacaoModel> transacaoModelOptional = transacaoService.findById(id);
        if (transacaoModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !");
        }
        BeanUtils.copyProperties(transacaoRecordDto, transacaoRecordDto.transacaoModel());
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.save(transacaoRecordDto.transacaoModel()));
    }


}
