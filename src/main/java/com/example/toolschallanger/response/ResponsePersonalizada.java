package com.example.toolschallanger.response;

import java.io.Serializable;

public record ResponsePersonalizada(int statusCode, Object message) implements Serializable {
}
