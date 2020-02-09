package com.lorescianatico.chain.util;

import com.lorescianatico.chain.fault.InvalidCatalogException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTypeTest {

    @Test
    public void fromValue() {
        assertEquals(ConfigType.XML, ConfigType.fromValue("XML"));
        assertEquals(ConfigType.XML, ConfigType.fromValue("xml"));
    }

    @Test(expected = InvalidCatalogException.class)
    public void unknownValue() {
        ConfigType.fromValue("unknownextension");
    }
}