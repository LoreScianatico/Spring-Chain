package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.fault.NotFoundException;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void save(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        recipe = recipeRepository.save(recipe);
        logger.info("Saved recipe: {}.", recipe.getName());
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
    public RecipeDto findByName(String name){
        Optional<Recipe> recipe = recipeRepository.findByName(name);
        return recipe.map(item -> {
            logger.info("Found recipe: {}.", item.getName());
            return recipeMapper.recipeToRecipeDto(item);
        }).orElseThrow(() -> {
            logger.warn("No recipe found with name: {}", name);
            return new NotFoundException();
        });
    }

    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        logger.info("Found {} recipe(s).", recipes.size());
        return recipes.stream().map(recipeMapper::recipeToRecipeDto).collect(Collectors.toList());
    }
}
