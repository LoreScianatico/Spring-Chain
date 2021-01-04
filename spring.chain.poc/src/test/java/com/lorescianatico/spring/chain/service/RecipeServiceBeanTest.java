package com.lorescianatico.spring.chain.service;

import com.lorescianatico.spring.chain.dto.IngredientDto;
import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.mapper.RecipeMapper;
import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import com.lorescianatico.spring.chain.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceBeanTest {

    @Mock
    private RecipeRepository recipeRepository;

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
        ingredient.setRecipe(recipe);
        recipe.setIngredients(Collections.singleton(ingredient));
        ReflectionTestUtils.setField(recipeServiceBean, "recipeMapper", Mappers.getMapper(RecipeMapper.class));



    }

    @Test
    void save() {
        when(recipeRepository.save(any(Recipe.class))).then(invocationOnMock -> invocationOnMock.getArgument(0));
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
    void patch() {
    }

    @Test
    void findByName() {

        when(recipeRepository.findByNameStartsWith(anyString())).thenReturn(Collections.singletonList(recipe));
        List<RecipeDto> recipeDtos = recipeServiceBean.findByName("name");
        assertNotNull(recipeDtos);
        assertNotNull(recipeDtos.get(0));

    }
}