package com.lorescianatico.chain.loader;

import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import com.lorescianatico.chain.stereotype.AnotherDummyHandler;
import com.lorescianatico.chain.stereotype.DummyHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class XMLChainLoaderTest {

    private XMLChainLoader xmlChainLoader;

    @BeforeEach
    public void setUp() {
        xmlChainLoader = new XMLChainLoader();
        ReflectionTestUtils.setField(xmlChainLoader, "handlers", Arrays.asList(new DummyHandler(), new AnotherDummyHandler()));
        xmlChainLoader.mapHandlers();
    }

    @Test
    void loadChain() {
        Map<String, DeclaredChain> chainMap = xmlChainLoader.loadChain("./src/test/resources/configuration.xml");
        assertEquals(2, chainMap.size());
        assertNotNull(chainMap.get("Chain"));
        assertNotNull(chainMap.get("AnotherChain"));
        assertNotNull(chainMap.get("Chain").getHandlers());
        assertEquals(2,chainMap.get("Chain").getHandlers().size());
    }

    @Test
    void testUndefinedHandler(){
        assertThrows(UndefinedHandlerException.class, () ->xmlChainLoader.loadChain("./src/test/resources/configurationWithUnknownHandler.xml"));
    }
}