package com.example.toolschallanger.exceptions;


import com.example.toolschallanger.response.ResponsePersonalizada;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class RequestsValidation {

    private static final Logger log = LogManager.getLogger(RequestsValidation.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Object> validaCamposNulosOuVazio(MethodArgumentNotValidException exception) {
        Map<String, Object> camposVazios = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(erro -> camposVazios.put(erro.getField(), erro.getDefaultMessage()));
        log.error("The fields are empty. " + camposVazios);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizada(HttpStatus.BAD_REQUEST.value(), camposVazios));
    }

    @ExceptionHandler(TransacaoBadRequest.class)
    public ResponseEntity<Object> transacao400(TransacaoBadRequest exception) {
        log.error("Error in the request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizada(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }

    @ExceptionHandler(TransacaoNaoEncontrada.class)
    public ResponseEntity<Object> transacao404(TransacaoNaoEncontrada exception) {
        log.error("Not Found a transaction.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePersonalizada(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }


}