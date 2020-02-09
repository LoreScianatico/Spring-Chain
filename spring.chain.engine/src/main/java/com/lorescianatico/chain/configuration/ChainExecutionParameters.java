package com.lorescianatico.chain.configuration;

import com.lorescianatico.chain.util.ConfigType;
import lombok.*;

import java.util.Arrays;

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

    public ConfigType getCatalogFileExtension(){
        return ConfigType.fromValue(getExtension());
    }

    private String getExtension() {
        return Arrays.stream(catalogFileLocation.split("\\."))
                .reduce("", (first,second) -> second);
    }

}
