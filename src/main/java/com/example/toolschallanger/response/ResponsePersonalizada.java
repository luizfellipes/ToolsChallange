package com.example.toolschallanger.response;

import java.io.Serializable;

public class ResponsePersonalizada implements Serializable {

    private int statusCode;
    private Object message;

    public ResponsePersonalizada(int statusCode, Object message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Object getMessage() {
        return message;
    }

}
