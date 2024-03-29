package com.lorescianatico.spring.chain.service;

import com.lorescianatico.chain.executor.ChainExecutor;
import com.lorescianatico.spring.chain.chain.RecipeSavingContext;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.fault.NotFoundException;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class RecipeServiceBean implements RecipeService {

    @Autowired
    @Lazy
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private ChainExecutor chainExecutor;

    @Override
    public void save(RecipeDto recipeDto) {
        RecipeSavingContext recipeSavingContext = new RecipeSavingContext();
        recipeSavingContext.setRecipeDto(recipeDto);
        chainExecutor.executeChain("recipe-saving-chain", recipeSavingContext);
        Recipe recipe = recipeSavingContext.getRecipe();
        recipe = recipeRepository.save(recipe);
        logger.info("Saved recipe: {}.", recipe.getName());
    }

    @Override
    public RecipeDto getById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        logger.info("Returning recipe.");
        return recipeMapper.toDto(recipe.orElseThrow(() -> {
            logger.warn("No recipe found with id: {}", id);
            return new NotFoundException();
        }));
    }

    @Override
    public RecipeDto findByName(String name){
        Optional<Recipe> recipe = recipeRepository.findByName(name);
        return recipe.map(item -> {
            logger.info("Found recipe: {}.", item.getName());
            return recipeMapper.toDto(item);
        }).orElseThrow(() -> {
            logger.warn("No recipe found with name: {}", name);
            return new NotFoundException();
        });
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        logger.info("Found {} recipe(s).", recipes.size());
        return recipes.stream().map(recipeMapper::toDto).collect(Collectors.toList());
    }
}
