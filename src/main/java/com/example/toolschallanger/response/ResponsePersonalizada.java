package com.example.toolschallanger.response;

import com.example.toolschallanger.models.entities.TransacaoModel;

import java.io.Serializable;
import java.util.List;

public class ResponsePersonalizada implements Serializable {

    private int statusCode;
    private String message;

    public ResponsePersonalizada(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

}
