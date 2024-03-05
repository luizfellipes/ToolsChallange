package com.example.toolschallanger.exceptions.validacoes;

import com.example.toolschallanger.response.ResponsePersonalizada;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RequestExceptionBadRequest {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizada(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
}
