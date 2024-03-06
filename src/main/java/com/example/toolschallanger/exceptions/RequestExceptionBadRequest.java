package com.example.toolschallanger.exceptions;

import com.example.toolschallanger.response.ResponsePersonalizada;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RequestExceptionBadRequest extends IllegalArgumentException {

    public RequestExceptionBadRequest() {
    }

    public RequestExceptionBadRequest(String message) {
        super(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizada(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
}
