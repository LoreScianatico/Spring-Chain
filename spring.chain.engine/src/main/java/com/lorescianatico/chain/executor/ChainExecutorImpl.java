package com.lorescianatico.chain.executor;

import com.lorescianatico.chain.configuration.ChainCatalog;
import com.lorescianatico.chain.configuration.ChainDefinition;
import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.fault.ChainExecutionException;
import com.lorescianatico.chain.fault.UndefinedChainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The chain executor class
 */
@Slf4j
@Component
@EnableConfigurationProperties({ChainCatalog.class, ChainDefinition.class})
final class ChainExecutorImpl implements ChainExecutor {

    private final Map<String, ChainDefinition> chainMap;

    public ChainExecutorImpl(ChainCatalog chainCatalog) {
        chainMap = chainCatalog.getChainDefinitions().stream().collect(Collectors.toMap(ChainDefinition::getChainName, Function.identity()));
        chainMap.keySet().forEach(key -> logger.info("Loaded chain: {}", key));
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
    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) {

        if (!chainMap.containsKey(chainName)){
            logger.error("Undefined chain: {}", chainName);
            throw new UndefinedChainException("Undefined chain: " + chainName);
        }

        logger.info("Executing chain: {}", chainName);
        ChainDefinition chain = chainMap.get(chainName);

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

    private <T extends AbstractChainContext> void executeHandler(Handler<T> handler, T chainContext) {
        String handlerName = handler.getClass().getName();
        logger.debug("Executing handler: {}", handlerName);
        chainContext.setLastRunningHandler(handlerName);
        handler.execute(chainContext);
        chainContext.setLastExecutedHandler(handlerName);
    }

}
