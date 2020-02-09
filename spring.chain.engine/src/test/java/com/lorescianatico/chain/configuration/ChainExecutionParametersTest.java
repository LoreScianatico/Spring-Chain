package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.util.ConfigType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChainExecutionParametersTest {

    @Test
    public void getCatalogFileExtension() {
        ChainExecutionParameters chainExecutionParameters = new ChainExecutionParameters();
        chainExecutionParameters.setCatalogFileLocation("file.xml");
        assertEquals(ConfigType.XML, chainExecutionParameters.getCatalogFileExtension());
    }

}