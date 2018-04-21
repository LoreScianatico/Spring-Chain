package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.fault.InvalidCatalogException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CatalogHolderTest {

    @Test
    public void getInstance() {
        CatalogHolder catalogHolder = CatalogHolder.getInstance();
        assertNotNull(catalogHolder);
    }

    @Test
    public void getCatalog() {
        CatalogHolder.getInstance().dropCatalog();
        Catalog catalog = CatalogHolder.getInstance().getCatalog("./src/test/resources/configuration.xml");
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
        CatalogHolder.getInstance().dropCatalog();
        Catalog catalog = CatalogHolder.getInstance().getCatalog("./src/test/resources/invalidConfiguration.xml");
    }

    @Test(expected = InvalidCatalogException.class)
    public void getInvalidCatalogDuplicateHandler() {
        CatalogHolder.getInstance().dropCatalog();
        Catalog catalog = CatalogHolder.getInstance().getCatalog("./src/test/resources/invalidConfigurationDuplicateHandler.xml");
    }

    @Test(expected = InvalidCatalogException.class)
    public void getInvalidCatalogDuplicateChain() {
        CatalogHolder.getInstance().dropCatalog();
        Catalog catalog = CatalogHolder.getInstance().getCatalog("./src/test/resources/invalidConfigurationDuplicateChain.xml");
    }
}