package com.lorescianatico.chain.configuration;

import lombok.*;

/**
 * The execution parameter bean
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChainExecutionParameters {

    /**
     * The location of configuration file
     */
    private String catalogFileLocation;

}
