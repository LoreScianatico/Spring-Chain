package com.lorescianatico.spring.chain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IngredientDto {

    private Long id;

    private Long version;

    private String name;

    private String description;

}
