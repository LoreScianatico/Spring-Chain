package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.handler.Handler;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The chain executor class
 */
@Slf4j
@Component
@NoArgsConstructor
public final class ChainExecutor {

    /**
     * The list of all available handlers
     */
    @Autowired
    private List<Handler> handlers;

    /**
     * The chain execution parameters
     */
    @Autowired
    private ChainExecutionParameters parameters;

    private Catalog catalog;

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    public void readCatalog(){

    }

    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext){

    }

}
