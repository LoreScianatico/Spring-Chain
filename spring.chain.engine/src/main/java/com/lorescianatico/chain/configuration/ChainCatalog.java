package com.lorescianatico.chain.configuration;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Getter
@ToString
@ConfigurationProperties(prefix = "chain-of-responsibility")
public class ChainCatalog {

    @NestedConfigurationProperty
    private final List<ChainDefinition> chainDefinitions;

    @ConstructorBinding
    public ChainCatalog(List<ChainDefinition> chainDefinitions) {
        this.chainDefinitions = chainDefinitions;
    }
}
