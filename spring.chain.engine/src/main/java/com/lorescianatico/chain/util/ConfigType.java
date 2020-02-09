package com.lorescianatico.chain.util;

import com.lorescianatico.chain.fault.InvalidCatalogException;

import java.util.Arrays;

public enum ConfigType {

    XML(TypeConstants.XML_LOADER);

    private final String loaderName;

    @Override
    public String toString() {
        return this.loaderName;
    }

    ConfigType(String loaderName) {
        this.loaderName = loaderName;
    }

    public static ConfigType fromValue(String valueString){
        ConfigType[] values = ConfigType.values();
        return Arrays.stream(values)
                .filter(value -> value.loaderName.equalsIgnoreCase(valueString))
                .findFirst()
                .orElseThrow(() -> new InvalidCatalogException("Unknown extension found: " + valueString));
    }
}
