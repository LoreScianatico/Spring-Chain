package com.lorescianatico.chain.executable;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeclaredChain {

    @Singular
    private List<Handler> handlers;

}
