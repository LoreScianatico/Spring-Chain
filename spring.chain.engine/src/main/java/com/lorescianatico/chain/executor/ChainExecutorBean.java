package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.CatalogReader;
import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.loader.ChainLoader;
import com.lorescianatico.chain.util.ChainLoaderQualifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(ChainLoaderQualifier.XML_QUALIFIER)
    private ChainLoader<Catalog, DeclaredChain> loader;

    private Map<String, DeclaredChain> chainMap = new HashMap<>();

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    void readCatalog(){
        chainMap.clear();
        Catalog catalog = CatalogReader.getCatalog(parameters.getCatalogFileLocation());
        putDeclaredChains(catalog);
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
            logger.error("An error occurred while processing chain: {}", e.getMessage());
            logger.error("Last executed handler was: {}", chainContext.getLastExecutedHandler());
            throw new ChainExecutionException("An error occurred while processing chain: " + e.getMessage(), e);
        }

        logger.info("Execution completed.");
    }

    private <T extends AbstractChainContext> void executeHandler(DeclaredHandler declaredHandler, T chainContext) {
        logger.debug("Executing handler: {}", declaredHandler.getClass().getName());
        declaredHandler.execute(chainContext);
        chainContext.setLastExecutedHandler(declaredHandler.getClass().getName());
    }

    /**
     * Fills the chain map with the catalog
     *
     * @param catalog the chain catalog
     */
    private void putDeclaredChains(Catalog catalog){
        chainMap.putAll(loader.loadChain(catalog));
    }

}
