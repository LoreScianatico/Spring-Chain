package com.lorescianatico.chain.stereotype;

import com.lorescianatico.chain.executable.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChainHandler
public class DummyHandler implements Handler<DummyContext> {

    @Override
    public void execute(DummyContext context) {
        logger.info("Executing dummy handler.");
    }

}
