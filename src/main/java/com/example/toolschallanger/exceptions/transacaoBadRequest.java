package com.example.toolschallanger.exceptions;

public class transacaoBadRequest extends IllegalArgumentException {

    public transacaoBadRequest() {
    }

    public transacaoBadRequest(String message) {
        super(message);
    }

}
