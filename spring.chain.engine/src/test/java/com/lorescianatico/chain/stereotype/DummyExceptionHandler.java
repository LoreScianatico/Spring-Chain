package com.lorescianatico.chain.stereotype;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChainHandler
public class DummyExceptionHandler implements DeclaredHandler {

    @Override
    public <T extends AbstractChainContext> void execute(T context) {
        logger.info("Will throw an exception...");
        throw new IllegalArgumentException("This is a controller exception.");
    }

}
