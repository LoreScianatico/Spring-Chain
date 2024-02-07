package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainCatalog;
import com.lorescianatico.chain.configuration.ChainDefinition;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyExceptionHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class ChainExecutorTest {

    public static final AbstractChainContext ABSTRACT_CHAIN_CONTEXT = new AbstractChainContext() {};

    private final List<DeclaredHandler> list = List.of(new DummyHandler(), new AnotherDummyHandler(), new DummyExceptionHandler());

    private ChainExecutorBean chainExecutorBean;

    @BeforeEach
    void setUp() {

        ChainDefinition chainDefinition1 = new ChainDefinition("Chain", List.of("com.lorescianatico.chain.stereotype.DummyHandler", "com.lorescianatico.chain.stereotype.AnotherDummyHandler"));
        ChainDefinition chainDefinition2 = new ChainDefinition("ChainWithException", List.of("com.lorescianatico.chain.stereotype.DummyHandler", "com.lorescianatico.chain.stereotype.DummyExceptionHandler"));
        ChainCatalog chainCatalog = new ChainCatalog(List.of(chainDefinition1, chainDefinition2));
        chainExecutorBean = new ChainExecutorBean(chainCatalog, list);
    }

    @Test
    void executeChain() {
        try {
            chainExecutorBean.executeChain("Chain", ABSTRACT_CHAIN_CONTEXT);
        } catch (ChainExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void executeUndefinedChain() {
        assertThrows(UndefinedChainException.class, () -> chainExecutorBean.executeChain("UndefinedChain", ABSTRACT_CHAIN_CONTEXT));
    }

    @Test
    void executeChainWithException() {

        assertThrows(ChainExecutionException.class, () -> chainExecutorBean.executeChain("ChainWithException", ABSTRACT_CHAIN_CONTEXT));

    }
}