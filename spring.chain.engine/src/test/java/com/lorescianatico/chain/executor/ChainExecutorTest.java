package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.loader.ChainLoader;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChainExecutorTest {

    @Mock
    private ChainExecutionParameters parameters;

    @Mock
    private ChainLoader<Catalog, DeclaredChain> loader;

    @InjectMocks
    private ChainExecutorBean chainExecutorBean;

    @Before
    public void setUp() {
        when(parameters.getCatalogFileLocation()).thenReturn("./src/test/resources/configuration.xml");
        Map<String, DeclaredChain> map = new HashMap<>();
        DeclaredChain.DeclaredChainBuilder builder = DeclaredChain.builder();
        builder.handlers(Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
        map.put("Chain", builder.build());
        when(loader.loadChain(any(Catalog.class))).thenReturn(map);
        chainExecutorBean.readCatalog();
    }

    @Test
    public void executeChain() {
        try {
            chainExecutorBean.executeChain("Chain", new AbstractChainContext() {});
        } catch (ChainExecutionException e) {
            fail(e.getMessage());
        }
    }

    @Test(expected = UndefinedChainException.class)
    public void executeUndefinedChain() {
        try {
            chainExecutorBean.executeChain("UndefinedChain", new AbstractChainContext() {});
        } catch (ChainExecutionException e) {
            fail(e.getMessage());
        }
    }
}