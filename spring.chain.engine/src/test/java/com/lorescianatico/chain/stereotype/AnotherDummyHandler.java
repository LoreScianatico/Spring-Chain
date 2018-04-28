package com.lorescianatico.chain.stereotype;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChainHandler
public class AnotherDummyHandler implements DeclaredHandler {

    @Override
    public <T extends AbstractChainContext> void execute(T context) throws ChainExecutionException {
        logger.info("Executing another dummy handler.");
    }

}
