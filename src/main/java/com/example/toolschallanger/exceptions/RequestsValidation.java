package com.example.toolschallanger.exceptions.validacoes;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


@RestControllerAdvice
public class RequestsValidation {

    @ExceptionHandler({MethodArgumentNotValidException.class, NullPointerException.class})
    private ResponseEntity<Object> validaCamposNulosOuVazio(Exception exception) {
        Map<String, Object> camposVazios = new HashMap<>();
        if (exception instanceof MethodArgumentNotValidException validacaoEx) {
            validacaoEx.getBindingResult().getFieldErrors().forEach(erro -> camposVazios.put(erro.getField(), erro.getDefaultMessage()));
        } else if (exception instanceof NullPointerException) {
            Stream.of("descricaoDePagamento", "formaDePagamento")
                    .filter(campo -> exception.getMessage().contains(campo))
                    .forEach(campo -> camposVazios.putIfAbsent(campo, "This field is required !"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(camposVazios);
    }

}