package com.lorescianatico.chain.executable;

import lombok.*;

import java.util.List;

/**
 * A declared chain with his handlers
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeclaredChain {

    /**
     * The handler list
     */
    @Singular
    private List<DeclaredHandler> handlers;

}
