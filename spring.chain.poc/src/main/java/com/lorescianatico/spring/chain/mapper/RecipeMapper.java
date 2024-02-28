package com.lorescianatico.spring.chain.mapper;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface RecipeMapper {

    RecipeDto recipeToRecipeDto(Recipe recipe);

    Recipe recipeDtoToRecipe(RecipeDto recipeDto);

    @AfterMapping
    default void linkObjects(@MappingTarget Recipe recipe){
        recipe.getIngredients().forEach(item -> item.setRecipe(recipe));
    }

}
