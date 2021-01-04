package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.fault.InvalidCatalogException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogReaderTest {

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

    @Test
    void getInvalidCatalog() {
        assertThrows(InvalidCatalogException.class, () -> CatalogReader.getCatalog("./src/test/resources/invalidConfiguration.xml"));
    }

    @Test
    void getInvalidCatalogDuplicateHandler() {
        assertThrows(InvalidCatalogException.class, () -> CatalogReader.getCatalog("./src/test/resources/invalidConfigurationDuplicateHandler.xml"));
    }

    @Test
    void getInvalidCatalogDuplicateChain() {
        assertThrows(InvalidCatalogException.class, () -> CatalogReader.getCatalog("./src/test/resources/invalidConfigurationDuplicateChain.xml"));
    }

    @Test
    void getInvalidCatalogNoFileFound(){
        assertThrows(InvalidCatalogException.class, () -> CatalogReader.getCatalog("./src/test/resources/thisdoesnotexists.xml"));
    }

    @Test
    void getInvalidCatalogNotAnXml(){
        assertThrows(InvalidCatalogException.class, () -> CatalogReader.getCatalog("./src/test/resources/notanxml.xml"));
    }
}