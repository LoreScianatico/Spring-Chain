package com.lorescianatico.chain.fault;

/**
 * Generic exception for chain processing errors
 */
public class ChainExecutionException extends Exception {

    /**
     * Constructor
     * @param message exception message
     */
    public ChainExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
