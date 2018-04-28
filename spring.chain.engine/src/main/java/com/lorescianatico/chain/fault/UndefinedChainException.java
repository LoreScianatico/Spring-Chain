package com.lorescianatico.chain.fault;

/**
 * Exception for undefined chain request
 */
public class UndefinedChainException extends RuntimeException {

    /**
     * Constructor
     * @param message the message
     */
    public UndefinedChainException(String message) {
        super(message);
    }

}
