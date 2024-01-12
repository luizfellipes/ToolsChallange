package com.example.toolschallanger.Config.Validacoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidaCampos {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> validaCamposNulosOuVazios(MethodArgumentNotValidException ex) {
        Map<String, String> camposVazios = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            camposVazios.put(erro.getField(), erro.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(camposVazios);
    }

}