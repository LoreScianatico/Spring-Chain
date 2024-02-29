package com.lorescianatico.spring.chain.mapper;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = IngredientMapper.class)
public interface RecipeMapper {

    RecipeDto toDto(Recipe recipe);

    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Recipe toEntity(RecipeDto recipeDto);

    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Recipe toEntity(RecipeDto recipeDto, @MappingTarget Recipe recipe);

    @AfterMapping
    default void linkObjects(@MappingTarget Recipe recipe){
        recipe.getIngredients().forEach(item -> item.setRecipe(recipe));
    }

}
