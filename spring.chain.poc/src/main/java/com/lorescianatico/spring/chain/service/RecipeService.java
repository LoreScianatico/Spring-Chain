package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;

import java.util.List;

public interface RecipeService {

    void save(RecipeDto recipeDto);

    RecipeDto getById(Long id);

    RecipeDto findByName(String name);

    List<RecipeDto> getAllRecipes();
}
