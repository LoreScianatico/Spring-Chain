package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;

public interface ChainExecutor {
    <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) throws ChainExecutionException;
}
