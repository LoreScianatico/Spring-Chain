package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
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
        ReflectionTestUtils.setField(recipeServiceBean, "recipeMapper", Mappers.getMapper(RecipeMapper.class));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeRepository.save(any(Recipe.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));
        when(recipeRepository.findByNameStartsWith(anyString())).thenReturn(Collections.singletonList(recipe));
    }

    @Test
    public void save() {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Sample");
        recipeDto.setId(1l);
        recipeDto.setVersion(1l);
        recipeDto.setDirections("Some directions over here");
        IngredientDto ingredient = new IngredientDto();
        ingredient.setName("Sample");
        ingredient.setId(1l);
        ingredient.setVersion(1l);
        ingredient.setDescription("Description");
        recipeDto.setIngredients(Collections.singletonList(ingredient));

        recipeServiceBean.save(recipeDto);
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

        List<RecipeDto> recipeDtos = recipeServiceBean.findByName("name");
        assertNotNull(recipeDtos);
        assertNotNull(recipeDtos.get(0));

    }
}