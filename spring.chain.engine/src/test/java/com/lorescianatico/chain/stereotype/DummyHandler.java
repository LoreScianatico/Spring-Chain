package com.lorescianatico.chain.stereotype;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChainHandler
public class DummyHandler implements Handler {

    @Override
    public <T extends AbstractChainContext> void execute(T context) {
        logger.info("Executing dummy handler.");
    }

}
