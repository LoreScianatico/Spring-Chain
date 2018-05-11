package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.CatalogReader;
import com.lorescianatico.chain.configuration.ChainExecutionParameters;
import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.configuration.model.Chain;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    private List<DeclaredHandler> handlers;

    /**
     * The chain execution parameters
     */
    @Autowired
    private ChainExecutionParameters parameters;

    private Map<String, DeclaredChain> chainMap = new HashMap<>();

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    public void readCatalog(){
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
    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) throws ChainExecutionException {

        if (!chainMap.containsKey(chainName)){
            logger.error("Undefined chain: {}", chainName);
            throw new UndefinedChainException("Undefined chain: " + chainName);
        }

        logger.info("Executing chain: {}", chainName);
        DeclaredChain chain = chainMap.get(chainName);

        for (DeclaredHandler handler: chain.getHandlers()){
            logger.debug("Executing handler: {}", handler.getClass().getName());
            handler.execute(chainContext);
            chainContext.setLastExecutedHandler(handler.getClass().getName());
        }

        logger.info("Execution completed.");
    }

    /**
     * Fills the chain map with the catalog
     *
     * @param catalog the chain catalog
     */
    private void putDeclaredChains(Catalog catalog){

        catalog.getChainList().getChain().forEach(chain -> {
            String chainName = chain.getChainName();
            List<DeclaredHandler> chainHandlers = getChainHandlers(chain.getHandlerList());
            logger.info("Loaded chain: {}", chainName);
            chainMap.put(chainName, new DeclaredChain(chainHandlers));
        });

    }

    /**
     * Gets handlers implementation by name
     * @param handlerList the list of handler to load for a chain
     * @return the list of {@link DeclaredHandler} corresponding to the handlers in catalog
     */
    private List<DeclaredHandler> getChainHandlers(Chain.HandlerList handlerList) {
        List<DeclaredHandler> chainHandlers = new ArrayList<>();

        handlerList.getHandler().forEach(handler -> {
            DeclaredHandler declaredHandler = findHandlerByName(handler.getValue());
            chainHandlers.add(declaredHandler);
        });

        logger.debug("Loaded {} handlers.", chainHandlers.size());

        return  chainHandlers;
    }

    /**
     * Gets the handler implementation based on configured name
     * @param value the handler to be found
     * @return the actual handler implementation
     */
    private DeclaredHandler findHandlerByName(String value) {
        for (DeclaredHandler handler: handlers){
            if (handler.getClass().getName().equals(value)){
                logger.debug("Handler found: {}", value);
                return handler;
            }
        }
        logger.error("No handler with name {}", value);
        throw new UndefinedHandlerException("No handler with name " + value);
    }

}
