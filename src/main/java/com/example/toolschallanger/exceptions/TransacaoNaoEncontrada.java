package com.example.toolschallanger.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class TransacaoNaoEncontrada extends EntityNotFoundException{

    public TransacaoNaoEncontrada() {
    }

    public TransacaoNaoEncontrada(String message) {
        super(message);
    }

}
