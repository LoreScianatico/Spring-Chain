package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    void save(RecipeDto recipeDto);

    RecipeDto getById(Long id);

    RecipeDto patch(RecipeDto recipeDto);

    List<RecipeDto> findByName(String name);

}
