package com.lorescianatico.chain.loader;


import com.lorescianatico.chain.executable.DeclaredChain;

import java.util.Map;

/**
 * Loader for chains
 */
public interface ChainLoader {

    /**
     * Loads a Map of chains
     *
     * @param source the chain definition source
     * @return The loaded chain map
     */
    Map<String, DeclaredChain> loadChain(String source);

}
