package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.stereotype.ChainHandler;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import lombok.extern.slf4j.Slf4j;

@ChainHandler
@Slf4j
public class RecipeRetrieverHandler implements Handler<RecipeSavingContext> {

    @Override
    public void execute(RecipeSavingContext context) {

        RecipeDto recipeDto = context.getRecipeDto();

        if (recipeDto.getId() == null){
            context.setRecipe(new Recipe());
        } else {
            var data = context.getRecipeRepository().findByName(recipeDto.getName());
            data.ifPresentOrElse(item -> {
                logger.info("Resolved recipe: {}", item.getName());
                context.setRecipe(item);
            }, () -> {
                logger.info("New recipe insertion {}", recipeDto.getName());
                context.setRecipe(new Recipe());
            });
        }

    }
}
