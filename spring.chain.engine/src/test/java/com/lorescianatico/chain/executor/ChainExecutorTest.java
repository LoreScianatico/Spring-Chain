package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.loader.ChainLoader;
import com.lorescianatico.chain.servicelocator.LoaderFactory;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyExceptionHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import com.lorescianatico.chain.util.ConfigType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChainExecutorTest {

    public static final AbstractChainContext ABSTRACT_CHAIN_CONTEXT = new AbstractChainContext() {};

    @Spy
    private ChainExecutionParameters parameters = ChainExecutionParameters.builder().catalogFileLocation("./src/test/resources/configuration.xml").build();

    @Mock
    private ChainLoader loader;

    @Mock
    private LoaderFactory loaderFactory;

    @InjectMocks
    private ChainExecutorBean chainExecutorBean;

    @BeforeEach
    void setUp() {
        when(loaderFactory.getLoader(any(ConfigType.class))).thenReturn(loader);
    }

    @Test
    void executeChain() {
        Map<String, DeclaredChain> map = new HashMap<>();
        DeclaredChain.DeclaredChainBuilder builder = DeclaredChain.builder();
        builder.handlers(Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
        map.put("Chain", builder.build());
        when(loader.loadChain(anyString())).thenReturn(map);
        chainExecutorBean.readCatalog();
        try {
            chainExecutorBean.executeChain("Chain", ABSTRACT_CHAIN_CONTEXT);
        } catch (ChainExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void executeUndefinedChain() {
        Map<String, DeclaredChain> map = new HashMap<>();
        DeclaredChain.DeclaredChainBuilder builder = DeclaredChain.builder();
        builder.handlers(Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
        map.put("Chain", builder.build());
        when(loader.loadChain(anyString())).thenReturn(map);
        chainExecutorBean.readCatalog();
        assertThrows(UndefinedChainException.class, () -> chainExecutorBean.executeChain("UndefinedChain", ABSTRACT_CHAIN_CONTEXT));
    }

    @Test
    void executeChainWithException() {
        Map<String, DeclaredChain> map = new HashMap<>();
        DeclaredChain.DeclaredChainBuilder builder = DeclaredChain.builder();
        builder.handlers(Arrays.asList(new DummyHandler(), new AnotherDummyHandler(), new DummyExceptionHandler()));
        map.put("Chain", builder.build());
        when(loader.loadChain(anyString())).thenReturn(map);
        chainExecutorBean.readCatalog();

        assertThrows(ChainExecutionException.class, () -> chainExecutorBean.executeChain("Chain", ABSTRACT_CHAIN_CONTEXT));

    }
}