package com.lorescianatico.spring.chain.mapper;

import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface IngredientMapper {

    Ingredient toEntity(IngredientDto ingredientDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    Ingredient toEntity(IngredientDto ingredientDto, @MappingTarget Ingredient ingredient);

    IngredientDto toDto(Ingredient ingredient);

}
