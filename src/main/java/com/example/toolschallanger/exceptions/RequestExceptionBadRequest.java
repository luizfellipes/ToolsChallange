package com.example.toolschallanger.exceptions;

import com.example.toolschallanger.response.ResponsePersonalizada;
import com.example.toolschallanger.services.TransacaoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RequestExceptionBadRequest extends IllegalArgumentException {

    private static final Logger log = LogManager.getLogger(RequestExceptionBadRequest.class);

    public RequestExceptionBadRequest() {
    }

    public RequestExceptionBadRequest(String message) {
        super(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException exception) {
        log.error("Error in the request.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponsePersonalizada(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));
    }
}
