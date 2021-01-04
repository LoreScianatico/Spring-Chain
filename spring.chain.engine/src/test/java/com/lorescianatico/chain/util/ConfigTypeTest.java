package com.lorescianatico.chain.util;

import com.lorescianatico.chain.fault.InvalidCatalogException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ConfigTypeTest {

    @Test
    void fromValue() {
        assertEquals(ConfigType.XML, ConfigType.fromValue("XML"));
        assertEquals(ConfigType.XML, ConfigType.fromValue("xml"));
    }

    @Test
    void unknownValue() {
        assertThrows(InvalidCatalogException.class, () -> ConfigType.fromValue("unknownextension"));
    }
}