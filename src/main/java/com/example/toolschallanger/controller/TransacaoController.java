package com.example.toolschallanger.controller;

import com.example.toolschallanger.models.dtos.TransacaoRecordDto;
import com.example.toolschallanger.models.entities.TransacaoModel;
import com.example.toolschallanger.models.enuns.Status;
import com.example.toolschallanger.services.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Criar", description = "Salvar novas transações", tags = "Transações")
    @PostMapping
    public ResponseEntity<TransacaoModel> save(@RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        if (transacaoRecordDto.transacaoModel().getDescricaoModel().getStatus() == Status.CANCELADO){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
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
        if (!transacaoService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista as transações por ID", description = "Busca as transações por ID", tags = "Transações")
    public ResponseEntity<Object> getOneTransacao(@PathVariable(value = "id") UUID id) {
        return transacaoService.findById(id).<ResponseEntity<Object>>map(model -> ResponseEntity.status(HttpStatus.OK).body(model))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta por transações por ID", description = "Deleta as transações por ID", tags = "Transações")
    public ResponseEntity<Object> deleteTransacao(@PathVariable(value = "id") UUID id) {
        if (transacaoService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !");
        } else {
            transacaoService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Transação deletada com sucesso !");
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza transações por ID", description = "Atualiza as transações por ID", tags = "Transações")
    public ResponseEntity<Object> updateTransacao(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransacaoRecordDto transacaoRecordDto) {
        if (transacaoService.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transação não encontrada !");
        }
        BeanUtils.copyProperties(transacaoRecordDto, transacaoRecordDto.transacaoModel());
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.save(transacaoRecordDto.transacaoModel()));
    }


}
