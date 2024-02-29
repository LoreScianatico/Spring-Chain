package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.stereotype.ChainHandler;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@ChainHandler
@Slf4j
public class RecipeRetrieverHandler implements Handler<RecipeSavingContext> {

    @Autowired
    @Lazy
    private RecipeRepository recipeRepository;

    @Override
    public void execute(RecipeSavingContext context) {

        RecipeDto recipeDto = context.getRecipeDto();

        if (recipeDto.getId() == null){
            context.setRecipe(new Recipe());
        } else {
            var data = recipeRepository.findByName(recipeDto.getName());
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
