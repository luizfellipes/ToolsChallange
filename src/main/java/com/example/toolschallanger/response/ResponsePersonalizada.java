package com.example.toolschallanger.response;

import java.io.Serializable;

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
