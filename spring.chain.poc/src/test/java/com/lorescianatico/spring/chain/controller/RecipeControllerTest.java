package com.lorescianatico.spring.chain.controller;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController recipeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void saveRecipe() {
    }

    @Test
    void getRecipeById() {
    }

    @Test
    void patchRecipe() {
    }

    @Test
    void getByName() {
    }


    @Test
    void getAllRecipes() throws Exception {

        List<RecipeDto> recipes = new ArrayList<>();
        recipes.add(new RecipeDto());

        RecipeDto recipe = new RecipeDto();
        recipe.setId(1L);

        recipes.add(recipe);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        //when
        mockMvc.perform(get("/recipe/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("recipes"))
                .andExpect(model().attribute("recipes", recipes));

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void getEmptyRecipe() throws Exception {

        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));

    }
}