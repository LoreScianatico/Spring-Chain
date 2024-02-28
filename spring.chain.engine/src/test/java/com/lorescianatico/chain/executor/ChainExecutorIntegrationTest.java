package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainCatalog;
import com.lorescianatico.chain.configuration.ChainDefinitionConverter;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ChainCatalog.class, ChainExecutorImpl.class, DummyHandler.class, AnotherDummyHandler.class, ChainDefinitionConverter.class})
@TestConfiguration(value = "application.yml")
class ChainExecutorIntegrationTest {

    @Autowired
    private ChainExecutor chainExecutor;

    @Test
    void testExecutor(){
        try {
            chainExecutor.executeChain("Chain", new AbstractChainContext() {});
        } catch (ChainExecutionException e) {
            fail();
        }
    }

}
