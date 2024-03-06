package com.example.toolschallanger.exceptions;

import com.example.toolschallanger.response.ResponsePersonalizada;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionNotFound extends EntityNotFoundException {

    private static final Logger log = LogManager.getLogger(RequestExceptionNotFound.class);

    public RequestExceptionNotFound() {
    }

    public RequestExceptionNotFound(String message) {
        super(message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException exception) {
        log.error("Not Found a transaction.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponsePersonalizada(HttpStatus.NOT_FOUND.value(), exception.getMessage()));
    }

}
