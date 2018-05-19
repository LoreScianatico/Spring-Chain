package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceBeanTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceBean recipeServiceBean;

    @Before
    public void setUp() {
        Recipe recipe = new Recipe("Sample");
        recipe.setId(1l);
        recipe.setVersion(1l);
        recipe.setDirections("Some directions over here");
        Ingredient ingredient = new Ingredient("Sample");
        ingredient.setId(1l);
        ingredient.setVersion(1l);
        ingredient.setDescription("Description");
        ingredient.setRecipe(recipe);
        recipe.setIngredients(Collections.singleton(ingredient));
        ReflectionTestUtils.setField(recipeServiceBean, "recipeMapper", RecipeMapper.instance);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
    }

    @Test
    public void save() {
    }

    @Test
    public void getById() {

        RecipeDto recipeDto = recipeServiceBean.getById(1l);
        assertNotNull(recipeDto);


    }

    @Test
    public void patch() {
    }

    @Test
    public void findByName() {
    }
}