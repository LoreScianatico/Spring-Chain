package com.lorescianatico.chain.executable;

import com.lorescianatico.chain.context.AbstractChainContext;

/**
 * Chain DeclaredHandler interface
 */
public interface Handler<T extends AbstractChainContext> {

    /**
     * Executes the handler on the context
     * @param context the context to process
     * @param <T> the concrete context class
     */
     void execute(T context);

}
