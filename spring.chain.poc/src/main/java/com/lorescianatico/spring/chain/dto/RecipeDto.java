package com.lorescianatico.spring.chain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeDto {

    private Long id;

    private Long version;

    private String name;

    private String directions;

    private List<IngredientDto> ingredients;

}
