package com.lorescianatico.chain.loader;

import com.lorescianatico.chain.configuration.model.Catalog;
import com.lorescianatico.chain.configuration.model.Chain;
import com.lorescianatico.chain.executable.DeclaredChain;
import com.lorescianatico.chain.executable.DeclaredHandler;
import com.lorescianatico.chain.fault.UndefinedHandlerException;
import com.lorescianatico.chain.util.ChainLoaderQualifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loader class for XML defined chains
 */
@Component
@Qualifier(ChainLoaderQualifier.XML_QUALIFIER)
@Slf4j
public class XMLChainLoader implements ChainLoader<Catalog, DeclaredChain> {

    /**
     * The list of all available handlers
     */
    @Autowired
    private List<DeclaredHandler> handlers;

    private Map<String, DeclaredHandler> handlerMap = new HashMap<>();

    @PostConstruct
    void mapHandlers(){
        handlerMap.clear();
        handlers.forEach(handler -> handlerMap.put(handler.getClass().getName(), handler));
    }

    @Override
    public Map<String, DeclaredChain> loadChain(Catalog catalog) {
        Map<String,DeclaredChain> chainMap = new HashMap<>();

        catalog.getChainList().getChain().forEach(chain -> {
            String chainName = chain.getChainName();
            List<DeclaredHandler> chainHandlers = getChainHandlers(chain.getHandlerList());
            logger.info("Loaded chain: {}", chainName);
            chainMap.put(chainName, new DeclaredChain(chainHandlers));
        });

        return chainMap;
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

        if(handlerMap.containsKey(value)){
            logger.debug("Handler found: {}", value);
            return handlerMap.get(value);
        }
        logger.error("No handler with name {}", value);
        throw new UndefinedHandlerException("No handler with name " + value);
    }
}
