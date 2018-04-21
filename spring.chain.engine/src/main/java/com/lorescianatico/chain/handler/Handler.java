package com.lorescianatico.chain.handler;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;

/**
 * Chain Handler interface
 */
public interface Handler {

    /**
     * The execute method interface
     * @param context the context containing chain data to process
     * @param <T> the context concrete type
     */
    <T extends AbstractChainContext> void execute(T context) throws ChainExecutionException;

}
