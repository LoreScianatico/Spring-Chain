package com.lorescianatico.chain.stereotype;

import com.lorescianatico.chain.executable.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChainHandler
public class AnotherDummyHandler implements Handler<DummyContext> {

    @Override
    public void execute(DummyContext context) {
        logger.info("Executing another dummy handler.");
    }

}
