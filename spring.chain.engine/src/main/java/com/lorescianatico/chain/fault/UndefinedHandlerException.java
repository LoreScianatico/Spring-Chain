package com.lorescianatico.chain.fault;

public class UndefinedHandlerException extends RuntimeException {
    public UndefinedHandlerException(String message) {
        super(message);
    }
}
