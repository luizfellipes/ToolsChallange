package com.example.toolschallanger.Config.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Excecoes {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> RuntimeException(RuntimeException ex) {
        Map<String, String> erros = new HashMap<>();
        erros.put("Erro encontrado :", ex.getMessage());
        return ResponseEntity.internalServerError().body(erros);
    }

}
