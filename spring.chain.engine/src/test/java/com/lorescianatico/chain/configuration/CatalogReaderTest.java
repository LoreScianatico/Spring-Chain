package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.fault.InvalidCatalogException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CatalogReaderTest {

    @Test
    public void getCatalog() {
        Catalog catalog = CatalogReader.getCatalog("./src/test/resources/configuration.xml");
        assertNotNull(catalog);
        assertNotNull(catalog.getChainList());
        assertEquals(2, catalog.getChainList().getChain().size());
        assertNotNull(catalog.getChainList().getChain().get(0));
        assertNotNull(catalog.getChainList().getChain().get(0).getHandlerList());
        assertNotNull(catalog.getChainList().getChain().get(0).getChainName());
        assertEquals(2, catalog.getChainList().getChain().get(0).getHandlerList().getHandler().size());
        assertNotNull(catalog.getChainList().getChain().get(0).getHandlerList().getHandler().get(0));
        assertNotNull(catalog.getChainList().getChain().get(0).getHandlerList().getHandler().get(0).getValue());
        assertNotNull(catalog.getChainList().getChain().get(0).getHandlerList().getHandler().get(0).getHandlerName());
    }

    @Test(expected = InvalidCatalogException.class)
    public void getInvalidCatalog() {
        Catalog catalog = CatalogReader.getCatalog("./src/test/resources/invalidConfiguration.xml");
    }

    @Test(expected = InvalidCatalogException.class)
    public void getInvalidCatalogDuplicateHandler() {
        Catalog catalog = CatalogReader.getCatalog("./src/test/resources/invalidConfigurationDuplicateHandler.xml");
    }

    @Test(expected = InvalidCatalogException.class)
    public void getInvalidCatalogDuplicateChain() {
        Catalog catalog = CatalogReader.getCatalog("./src/test/resources/invalidConfigurationDuplicateChain.xml");
    }
}