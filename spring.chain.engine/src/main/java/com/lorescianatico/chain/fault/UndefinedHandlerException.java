package com.lorescianatico.chain.fault;

/**
 * Exception class for undefined handler error
 */
public class UndefinedHandlerException extends RuntimeException {

    /**
     * Constructor
     * @param message the message
     */
    public UndefinedHandlerException(String message) {
        super(message);
    }
}
