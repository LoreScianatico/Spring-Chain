package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.stereotype.ChainHandler;
import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.mapper.IngredientMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@ChainHandler
@Slf4j
public class IngredientMapperHandler implements Handler<RecipeSavingContext> {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public void execute(RecipeSavingContext context) {

        List<IngredientDto> dtos = context.getRecipeDto().getIngredients();
        Set<Ingredient> ingredients = context.getRecipe().getIngredients();

        dtos.forEach(item -> {
            Ingredient match = ingredients.stream()
                    .filter(entity -> entity.getName().equals(item.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        logger.info("New ingredient introduced!");
                        return new Ingredient();
                    });
            match = ingredientMapper.toEntity(item, match);
            match.setRecipe(context.getRecipe());
            ingredients.add(match);
        });

    }
}
