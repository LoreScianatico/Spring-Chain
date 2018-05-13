package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChainExecutorTest {

    @Mock
    private ChainExecutionParameters parameters;

    @InjectMocks
    private ChainExecutorBean chainExecutorBean;

    @Before
    public void setUp() throws Exception {
        when(parameters.getCatalogFileLocation()).thenReturn("./src/test/resources/configuration.xml");
        ReflectionTestUtils.setField(chainExecutorBean, "handlers", Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
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