package com.lorescianatico.chain.executable;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;

/**
 * Chain DeclaredHandler interface
 */
public interface DeclaredHandler {

    /**
     * Executes the handler on the context
     * @param context the context to process
     * @param <T> the concrete context class
     * @throws ChainExecutionException if an error occurs
     */
    <T extends AbstractChainContext> void execute(T context) throws ChainExecutionException;

}
