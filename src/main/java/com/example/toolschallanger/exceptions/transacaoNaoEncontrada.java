package com.example.toolschallanger.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class transacaoNaoEncontrada extends EntityNotFoundException{

    public transacaoNaoEncontrada() {
    }

    public transacaoNaoEncontrada(String message) {
        super(message);
    }

}
