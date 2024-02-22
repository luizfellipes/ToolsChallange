package com.example.toolschallanger.controller;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.services.TransacaoService;
import com.example.toolschallanger.models.entities.TransacaoModel;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<TransacaoModel> save(@Valid @RequestBody TransacaoRecordDTO transacaoRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.save(transacaoRecordDto));
    }

    @PostMapping("/estorno/{id}")
    @Operation(summary = "Estorno", description = "Estorna as transacoes autorizadas ou erradas transações", tags = "Transações")
    public ResponseEntity<Object> estorno(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.estorno(id));
    }

    @GetMapping
    @Operation(summary = "Lista as transações", description = "Busca todas as transações", tags = "Transações")
    public ResponseEntity<List<TransacaoModel>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista as transações por ID", description = "Busca as transações por ID", tags = "Transações")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta por transações por ID", description = "Deleta as transações por ID", tags = "Transações")
    public ResponseEntity<Object> delete(@PathVariable(value = "id")  UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.deleteById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza transações por ID", description = "Atualiza as transações por ID", tags = "Transações")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransacaoRecordDTO transacaoRecordDto) {
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.updateById(id, transacaoRecordDto));
    }

}
