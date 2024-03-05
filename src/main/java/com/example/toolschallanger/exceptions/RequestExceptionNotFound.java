package com.example.toolschallanger.exceptions.validacoes;

import com.example.toolschallanger.response.ResponsePersonalizada;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RequestExceptionNotFound extends Exception {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePersonalizada(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

}
