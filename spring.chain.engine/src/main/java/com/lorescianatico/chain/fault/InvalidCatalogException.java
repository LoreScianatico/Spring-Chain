package com.lorescianatico.chain.fault;

/**
 * Exception for throwing errors related to catalog invalid configuration
 */
public class InvalidCatalogException extends RuntimeException {

    /**
     * Constructor
     * @param message the exception message
     */
    public InvalidCatalogException(String message) {
        super(message);
    }

}
