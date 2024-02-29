package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.stereotype.ChainHandler;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;

@ChainHandler
public class RecipeMapperHandler implements Handler<RecipeSavingContext> {

    @Autowired
    private RecipeMapper recipeMapper;

    @Override
    public void execute(RecipeSavingContext context) {

        Recipe recipe = recipeMapper.toEntity(context.getRecipeDto(), context.getRecipe());
        context.setRecipe(recipe);

    }
}
