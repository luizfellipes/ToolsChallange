package com.example.toolschallanger.controller;

import com.example.toolschallanger.models.dtos.TransacaoRecordDTO;
import com.example.toolschallanger.services.TransacaoService;
import com.example.toolschallanger.models.entities.TransacaoModel;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private static final Logger log = LogManager.getLogger(TransacaoController.class);

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Criar", description = "Salvar novas transações", tags = "Transações")
    @PostMapping
    public ResponseEntity<TransacaoModel> save(@Valid @RequestBody TransacaoRecordDTO transacaoRecordDto) {
        log.info("Creating a new transaction.");
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoService.save(transacaoRecordDto));
    }

    @PostMapping("/estorno/{id}")
    @Operation(summary = "Estorno", description = "Estorna as transacoes autorizadas ou erradas transações", tags = "Transações")
    public ResponseEntity<TransacaoModel> estorno(@PathVariable(value = "id") UUID id) {
        log.info("Reversing the following ID: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.estorno(id));
    }

    @GetMapping
    @Operation(summary = "Lista as transações", description = "Busca todas as transações", tags = "Transações")
    public ResponseEntity<Page<TransacaoModel>> getAll(@PageableDefault(size=10) Pageable pageable) {
        log.info("Seaching all transactions.");
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista as transações por ID", description = "Busca as transações por ID", tags = "Transações")
    public ResponseEntity<Optional<TransacaoModel>> getOne(@PathVariable(value = "id") UUID id) {
        log.info("Fetching the following ID: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta por transações por ID", description = "Deleta as transações por ID", tags = "Transações")
    public ResponseEntity<?> delete(@PathVariable(value = "id") UUID id) {
        log.info("Deleting the following ID: " + id);
        transacaoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza transações por ID", description = "Atualiza as transações por ID", tags = "Transações")
    public ResponseEntity<TransacaoModel> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid TransacaoRecordDTO transacaoRecordDto) {
        log.info("Updating the following ID: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(transacaoService.updateById(id, transacaoRecordDto));
    }

}
