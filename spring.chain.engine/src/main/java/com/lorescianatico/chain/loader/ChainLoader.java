package com.lorescianatico.chain.loader;


import java.util.Map;

/**
 * Loader for chains
 *
 * @param <T> The type of chain definition sources
 * @param <V> The type for chain implementation
 */
public interface ChainLoader<T, V> {

    /**
     * Loads a Map of chains
     *
     * @param source the chain definition source
     * @return The loaded chain map
     */
    Map<String, V> loadChain(T source);

}
