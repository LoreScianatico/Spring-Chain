package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainCatalog;
import com.lorescianatico.chain.configuration.ChainDefinition;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyContext;
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

    private final List<Handler> list = List.of(new DummyHandler(), new AnotherDummyHandler(), new DummyExceptionHandler());

    private ChainExecutorImpl chainExecutorImpl;

    @BeforeEach
    void setUp() {

        ChainDefinition chainDefinition1 = new ChainDefinition("Chain", List.of(new DummyHandler(), new AnotherDummyHandler()));
        ChainDefinition chainDefinition2 = new ChainDefinition("ChainWithException", List.of(new DummyHandler(), new AnotherDummyHandler(), new DummyExceptionHandler()));
        ChainCatalog chainCatalog = new ChainCatalog(List.of(chainDefinition1, chainDefinition2));
        chainExecutorImpl = new ChainExecutorImpl(chainCatalog);
    }

    @Test
    void executeChain() {
        try {
            chainExecutorImpl.executeChain("Chain", new DummyContext());
        } catch (ChainExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void executeUndefinedChain() {
        assertThrows(UndefinedChainException.class, () -> chainExecutorImpl.executeChain("UndefinedChain", new DummyContext()));
    }

    @Test
    void executeChainWithException() {

        assertThrows(ChainExecutionException.class, () -> chainExecutorImpl.executeChain("ChainWithException", new DummyContext()));

    }
}