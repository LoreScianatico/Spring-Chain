package com.lorescianatico.spring.chain.controller;

import com.lorescianatico.spring.chain.dto.RecipeDto;
import com.lorescianatico.spring.chain.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Void> saveRecipe(@RequestBody RecipeDto recipeDto){
        recipeService.save(recipeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public RecipeDto getRecipeById(@PathVariable Long id){
        return recipeService.getById(id);
    }

    @GetMapping("{name}")
    public RecipeDto getByName(@PathVariable String name){
        return recipeService.findByName(name);
    }

    @GetMapping("all")
    public List<RecipeDto> getAllRecipes(){

        return recipeService.getAllRecipes();

    }
}
