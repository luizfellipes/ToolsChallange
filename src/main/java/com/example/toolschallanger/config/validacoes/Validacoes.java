package com.example.toolschallanger.config.validacoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Validacoes {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> validaCamposNulosOuVazios(MethodArgumentNotValidException ex) {
        Map<String, String> camposVazios = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            camposVazios.put(erro.getField(), erro.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camposVazios);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> runtimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro encontrado: " + exception.getLocalizedMessage());
    }

}