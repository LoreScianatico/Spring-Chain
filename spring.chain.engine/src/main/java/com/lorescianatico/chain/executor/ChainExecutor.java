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
import com.lorescianatico.chain.util.StringBuilderUtil;
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

    private Map<String, DeclaredChain> chainMap;

    /**
     * Reads and loads the catalog
     */
    @PostConstruct
    public void readCatalog(){
        chainMap = new HashMap<>();
        Catalog catalog = CatalogReader.getInstance().getCatalog(parameters.getCatalogFileLocation());
        putDeclaredChains(chainMap, catalog);
    }

    public <T extends AbstractChainContext> void executeChain(String chainName, T chainContext) throws ChainExecutionException {

        if (!chainMap.containsKey(chainName)){
            logger.error("Undefined chain: " + chainName);
            throw new UndefinedChainException("Undefined chain: " + chainName);
        }

        logger.info(StringBuilderUtil.build("Executing chain: ", chainName));
        DeclaredChain chain = chainMap.get(chainName);

        for (DeclaredHandler handler: chain.getHandlers()){
            logger.debug(StringBuilderUtil.build("Executing handler: ", handler.getClass().getName()));
            handler.execute(chainContext);
            chainContext.setLastExecutedHandler(handler.getClass().getName());
        }

        logger.info("Execution completed.");
    }

    private void putDeclaredChains(Map<String, DeclaredChain>chainMap, Catalog catalog){

        catalog.getChainList().getChain().forEach(chain -> {
            String chainName = chain.getChainName();
            List<DeclaredHandler> chainHandlers = getChainHandlers(handlers, chain.getHandlerList());
            logger.info(StringBuilderUtil.build("Loaded chain: ", chainName));
            chainMap.put(chainName, new DeclaredChain(chainHandlers));
        });

    }

    private List<DeclaredHandler> getChainHandlers(List<DeclaredHandler> handlers, Chain.HandlerList handlerList) {
        List<DeclaredHandler> chainHandlers = new ArrayList<>();

        handlerList.getHandler().forEach(handler -> {
            DeclaredHandler declaredHandler = findHandlerByName(handlers, handler.getValue());
            chainHandlers.add(declaredHandler);
        });

        logger.debug(StringBuilderUtil.build("Loaded ", Integer.toString(chainHandlers.size()), " handlers."));

        return  chainHandlers;
    }

    private DeclaredHandler findHandlerByName(List<DeclaredHandler> handlers, String value) {
        for (DeclaredHandler handler: handlers){
            if (handler.getClass().getName().equals(value)){
                logger.debug(StringBuilderUtil.build("Handler found: ", value));
                return handler;
            }
        }
        logger.error("No handler with name " + value);
        throw new UndefinedHandlerException("No handler with name " + value);
    }

}
