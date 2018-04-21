package com.lorescianatico.chain.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provider for configuration
 */
@Configuration
public class ChainExecutionConfiguration {

    /**
     * The catalog property value
     */
    @Value("${catalogfile}")
    private String catalogFile;

    /**
     * Gets the chain execution configuration
     * @return the configuration wrapper bean
     */
    @Bean
    public ChainExecutionParameters chainExecutionParameters(){
        ChainExecutionParameters.ChainExecutionParametersBuilder builder = new ChainExecutionParameters.ChainExecutionParametersBuilder();
        builder.catalogFileLocation(catalogFile);
        return builder.build();
    }

}
