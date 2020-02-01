package com.lorescianatico.spring.chain.repository;

import com.lorescianatico.spring.chain.model.Ingredient;
import com.lorescianatico.spring.chain.model.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        Recipe recipe = new Recipe("Sample recipe");
        Ingredient ingredient = new Ingredient("Sample ingredient");
        recipe.setIngredients(Collections.singleton(ingredient));
        recipeRepository.save(recipe);
    }

    @Test
    public void findByName() {

        List<Recipe> recipes = recipeRepository.findByName("Sample recipe");
        assertTrue(!recipes.isEmpty());
        assertEquals(1, recipes.size());

    }

    @Test
    public void findByNameStartsWith() {

        List<Recipe> recipes = recipeRepository.findByNameStartsWith("Sample");
        assertTrue(!recipes.isEmpty());
        assertEquals(1, recipes.size());

    }
}