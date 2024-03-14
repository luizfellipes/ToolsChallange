package com.example.toolschallanger.exceptions;

public class TransacaoBadRequest extends IllegalArgumentException {

    public TransacaoBadRequest() {
    }

    public TransacaoBadRequest(String message) {
        super(message);
    }

}
