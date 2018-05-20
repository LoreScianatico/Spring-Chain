package com.lorescianatico.spring.chain.mapper;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import org.mapstruct.Mapper;

@Mapper
public interface RecipeMapper {

    RecipeDto recipeToRecipeDto(Recipe recipe);

    Recipe recipeDtoToRecipe(RecipeDto recipeDto);

}
