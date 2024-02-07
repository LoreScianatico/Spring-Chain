package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainCatalog;
import com.lorescianatico.chain.configuration.ChainDefinition;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The chain executor class
 */
@Slf4j
@Component
@EnableConfigurationProperties({ChainCatalog.class, ChainDefinition.class})
public final class ChainExecutorBean implements ChainExecutor {

    private final ChainCatalog chainCatalog;

    private final Map<String, DeclaredHandler> handlerMap = new HashMap<>();

    private final Map<String, DeclaredChain> chainMap = new HashMap<>();

    public ChainExecutorBean(ChainCatalog chainCatalog, List<DeclaredHandler> handlers) {
        this.chainCatalog = chainCatalog;
        handlers.forEach(handler -> handlerMap.put(handler.getClass().getName(), handler));
        chainMap.putAll(loadChain());
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

    public Map<String, DeclaredChain> loadChain() {
        Map<String,DeclaredChain> chainMap = new HashMap<>();

        chainCatalog.getChainDefinitions().forEach(chain -> {
            String chainName = chain.getChainName();
            List<DeclaredHandler> chainHandlers = getChainHandlers(chain.getHandlers());
            logger.info("Loaded chain: {}", chainName);
            chainMap.put(chainName, new DeclaredChain(chainHandlers));
        });

        return chainMap;
    }

    private List<DeclaredHandler> getChainHandlers(List<String> handlerList) {
        List<DeclaredHandler> chainHandlers = new ArrayList<>();

        handlerList.forEach(handler -> {
            DeclaredHandler declaredHandler = findHandlerByName(handler);
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

        if(handlerMap.containsKey(value)){
            logger.debug("Handler found: {}", value);
            return handlerMap.get(value);
        }
        logger.error("No handler with name {}", value);
        throw new UndefinedHandlerException("No handler with name " + value);
    }
}
