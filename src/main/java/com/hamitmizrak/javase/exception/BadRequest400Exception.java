package com.hamitmizrak.javase.exception;

public class BadRequest400Exception extends RuntimeException{
    public BadRequest400Exception(String message) {
        super(message);
    }
}
