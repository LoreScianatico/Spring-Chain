package com.lorescianatico.chain.context;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract Context Class for chain
 */
@Getter
@Setter
public abstract class AbstractChainContext {

    /**
     * Name of the last chain handler executed on this field
     */
    private String lastExecutedHandler;

}
