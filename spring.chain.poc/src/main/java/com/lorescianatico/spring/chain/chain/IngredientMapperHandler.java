package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.executable.Handler;
import com.lorescianatico.chain.stereotype.ChainHandler;
import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.mapper.IngredientMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@ChainHandler
@Slf4j
public class IngredientMapperHandler implements Handler<RecipeSavingContext> {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    @Lazy
    private IngredientRepository ingredientRepository;

    @Override
    public void execute(RecipeSavingContext context) {

        List<IngredientDto> dtos = context.getRecipeDto().getIngredients();
        List<Ingredient> ingredients = ingredientRepository.findByNameIn(dtos.stream().map(IngredientDto::getName).toList());

        dtos.forEach(item -> {
            Ingredient match = ingredients.stream()
                    .filter(entity -> entity.getName().equals(item.getName()))
                    .findFirst()
                    .orElseGet(() -> {
                        logger.info("New ingredient introduced!");
                        return new Ingredient();
                    });
            match = ingredientMapper.toEntity(item, match);
            match.addRecipe(context.getRecipe());
            ingredients.add(match);
        });

    }
}
