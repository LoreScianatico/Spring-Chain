package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.servicelocator.LoaderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * The chain executor class
 */
@Slf4j
@Component
public final class ChainExecutorBean implements ChainExecutor {

    /**
     * The chain execution parameters
     */
    @Autowired
    private ChainExecutionParameters parameters;

    @Autowired
    private LoaderFactory loaderFactory;

    private Map<String, DeclaredChain> chainMap = new HashMap<>();

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    void readCatalog(){
        chainMap.clear();
        chainMap.putAll(loaderFactory.getLoader(parameters.getCatalogFileExtension()).loadChain(parameters.getCatalogFileLocation()));
    }

    /**
     * Executes a Chain on a context
     *
     * @param chainName the name of the chain
     * @param chainContext the context to be processed
     * @param <T> the context type
     * @throws ChainExecutionException if an handler throws an exception
     */
    @Override
    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) throws ChainExecutionException {

        if (!chainMap.containsKey(chainName)){
            logger.error("Undefined chain: {}", chainName);
            throw new UndefinedChainException("Undefined chain: " + chainName);
        }

        logger.info("Executing chain: {}", chainName);
        DeclaredChain chain = chainMap.get(chainName);

        try {
            chain.getHandlers().forEach(declaredHandler -> executeHandler(declaredHandler, chainContext));
        } catch (Exception e) {
            logger.error("An error occurred while processing handler {} from chain {}: {}",
                    chainContext.getLastRunningHandler(), chainName, e.getMessage());
            logger.error("Last executed handler was: {}", chainContext.getLastExecutedHandler());
            throw new ChainExecutionException("An error occurred while processing chain: " + e.getMessage(), e);
        }

        logger.info("Execution completed.");
    }

    private <T extends AbstractChainContext> void executeHandler(DeclaredHandler declaredHandler, T chainContext) {
        String handlerName = declaredHandler.getClass().getName();
        logger.debug("Executing handler: {}", handlerName);
        chainContext.setLastRunningHandler(handlerName);
        declaredHandler.execute(chainContext);
        chainContext.setLastExecutedHandler(handlerName);
    }

}
