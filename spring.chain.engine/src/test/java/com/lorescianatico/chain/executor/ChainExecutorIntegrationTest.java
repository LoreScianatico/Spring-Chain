package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionConfiguration;
import com.lorescianatico.chain.configuration.LoaderConfig;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.loader.XMLChainLoader;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChainExecutorBean.class, DummyHandler.class, AnotherDummyHandler.class,
        ChainExecutionConfiguration.class, XMLChainLoader.class, LoaderConfig.class},
        properties = {"catalogfile = ./src/test/resources/configuration.xml"})
public class ChainExecutorIntegrationTest {

    @Autowired
    private ChainExecutor chainExecutor;

    @Test
    public void testExecutor(){
        try {
            chainExecutor.executeChain("Chain", new AbstractChainContext() {});
        } catch (ChainExecutionException e) {
            fail();
        }
    }

}
