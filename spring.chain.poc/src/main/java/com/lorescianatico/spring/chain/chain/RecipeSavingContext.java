package com.lorescianatico.spring.chain.chain;

import com.lorescianatico.chain.context.AbstractChainContext;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeSavingContext extends AbstractChainContext {

    private RecipeDto recipeDto;

    private Recipe recipe;

    private RecipeRepository recipeRepository; //to avoid circular dependency

}
