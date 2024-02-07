package com.lorescianatico.chain.configuration;

import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@ConfigurationProperties
@Getter
@ToString
public class ChainDefinition {

    private final String chainName;

    private final List<String> handlers;

    @ConstructorBinding
    public ChainDefinition(String chainName, List<String> handlers) {
        this.chainName = chainName;
        this.handlers = handlers;
    }

}
