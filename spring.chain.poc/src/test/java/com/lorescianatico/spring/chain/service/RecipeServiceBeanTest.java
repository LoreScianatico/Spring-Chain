package com.lorescianatico.spring.chain.service;

import com.lorescianatico.chain.executor.ChainExecutor;
import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.mapper.IngredientMapper;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceBeanTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ChainExecutor chainExecutor;

    @InjectMocks
    private RecipeServiceBean recipeServiceBean;
    private Recipe recipe;

    @BeforeEach
    void setUp() {
        recipe = new Recipe("Sample");
        recipe.setId(1L);
        recipe.setVersion(1L);
        recipe.setDirections("Some directions over here");
        Ingredient ingredient = new Ingredient("Sample");
        ingredient.setId(1L);
        ingredient.setVersion(1L);
        ingredient.setDescription("Description");
        ingredient.addRecipe(recipe);
        recipe.setIngredients(Collections.singleton(ingredient));
        RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);
        IngredientMapper ingredientMapper = Mappers.getMapper(IngredientMapper.class);
        ReflectionTestUtils.setField(recipeMapper, "ingredientMapper", ingredientMapper);
        ReflectionTestUtils.setField(recipeServiceBean, "recipeMapper", recipeMapper);



    }

    @Test
    void save() {
        when(recipeRepository.save(ArgumentMatchers.isNull())).thenReturn(new Recipe());
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Sample");
        recipeDto.setId(1L);
        recipeDto.setVersion(1L);
        recipeDto.setDirections("Some directions over here");
        IngredientDto ingredient = new IngredientDto();
        ingredient.setName("Sample");
        ingredient.setId(1L);
        ingredient.setVersion(1L);
        ingredient.setDescription("Description");
        recipeDto.setIngredients(Collections.singletonList(ingredient));

        recipeServiceBean.save(recipeDto);
    }

    @Test
    void getById() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        RecipeDto recipeDto = recipeServiceBean.getById(1L);
        assertNotNull(recipeDto);

    }


    @Test
    void findByName() {

        when(recipeRepository.findByName(anyString())).thenReturn(Optional.of(new Recipe()));
        RecipeDto recipeDtos = recipeServiceBean.findByName("name");
        assertNotNull(recipeDtos);

    }
}