package com.lorescianatico.chain.loader;

import com.lorescianatico.chain.configuration.CatalogReader;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class XMLChainLoaderTest {

    private XMLChainLoader xmlChainLoader;

    @Before
    public void setUp() {
        xmlChainLoader = new XMLChainLoader();
        ReflectionTestUtils.setField(xmlChainLoader, "handlers", Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
    }

    @Test
    public void loadChain() {
        Catalog catalog =CatalogReader.getCatalog("./src/test/resources/configuration.xml");
        Map<String, DeclaredChain> chainMap = xmlChainLoader.loadChain(catalog);
        assertEquals(2, chainMap.size());
        assertNotNull(chainMap.get("Chain"));
        assertNotNull(chainMap.get("AnotherChain"));
        assertNotNull(chainMap.get("Chain").getHandlers());
        assertEquals(2,chainMap.get("Chain").getHandlers().size());
    }

    @Test(expected = UndefinedHandlerException.class)
    public void testUndefinedHandler(){
        Catalog catalog =CatalogReader.getCatalog("./src/test/resources/configurationWithUnknownHandler.xml");
        Map<String, DeclaredChain> chainMap = xmlChainLoader.loadChain(catalog);
    }
}