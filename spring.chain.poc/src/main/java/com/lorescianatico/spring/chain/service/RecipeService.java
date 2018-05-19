package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;

public interface RecipeService {

    void save(RecipeDto recipeDto);

    RecipeDto getById(Long id);

    RecipeDto patch(RecipeDto recipeDto);

}
