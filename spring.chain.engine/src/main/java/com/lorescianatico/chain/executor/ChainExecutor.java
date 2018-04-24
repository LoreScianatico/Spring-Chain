package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.CatalogHolder;
import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<String, DeclaredChain> chainMap;

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    public void readCatalog(){
        chainMap = new HashMap<>();
        Catalog catalog = CatalogHolder.getInstance().getCatalog(parameters.getCatalogFileLocation());

    }

    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) throws ChainExecutionException {

        if (!chainMap.containsKey(chainName)){
            logger.error("Undefined chain: " + chainName);
            throw new UndefinedChainException("Undefined chain: " + chainName);
        }

        logger.info("Executing chain: ", chainName);
        DeclaredChain chain = chainMap.get(chainName);

        for (Handler handler: chain.getHandlers()){
            logger.debug("Executing handler: ", handler.getClass().getName());
            handler.execute(chainContext);
        }

        logger.info("Execution completed.");
    }

}
