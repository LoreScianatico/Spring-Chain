package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.fault.NotFoundException;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RecipeServiceBean implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void save(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        recipeRepository.save(recipe);
        logger.info("Saved recipe.");
    }

    @Override
    public RecipeDto getById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        logger.info("Returning recipe.");
        return recipeMapper.recipeToRecipeDto(recipe.orElseThrow(() -> {
            logger.warn("No recipe found with id: {}", id);
            return new NotFoundException();
        }));
    }

    @Override
    public RecipeDto patch(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        Recipe patched = recipeRepository.save(recipe);
        logger.info("Patched recipe.");
        return recipeMapper.recipeToRecipeDto(patched);
    }

    @Override
    public List<RecipeDto> findByName(String name){
        List<Recipe> recipes = recipeRepository.findByNameStartsWith(name);
        logger.info("Found {} recipe(s).", recipes.size());
        return recipes.stream().map(recipeMapper::recipeToRecipeDto).collect(Collectors.toList());
    }
}
