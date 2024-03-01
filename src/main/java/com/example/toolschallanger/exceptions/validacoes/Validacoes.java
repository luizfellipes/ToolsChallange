package com.example.toolschallanger.exceptions.validacoes;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@RestControllerAdvice
public class Validacoes {

    @ExceptionHandler({MethodArgumentNotValidException.class, NullPointerException.class})
    private ResponseEntity<Object> validaCamposNulosOuVazio(Exception exception) {
        Map<String, Object> camposVazios = new HashMap<>();
        if (exception instanceof MethodArgumentNotValidException validacaoEx) {
            validacaoEx.getBindingResult().getFieldErrors().forEach(erro -> camposVazios.put(erro.getField(), erro.getDefaultMessage()));
        } else if (exception instanceof NullPointerException) {
            Stream.of("descricaoDePagamento", "formaDePagamento")
                    .filter(campo -> exception.getMessage().contains(campo))
                    .forEach(campo -> camposVazios.putIfAbsent(campo, "Este campo é obrigatório"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camposVazios);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<Object> EntityNotFoundException(EntityNotFoundException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("StatusCode", HttpStatus.NOT_FOUND.value());
        errorResponse.put("Detalhes", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<Object> IllegalArgumentException(IllegalArgumentException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("StatusCode", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("Detalhes", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}