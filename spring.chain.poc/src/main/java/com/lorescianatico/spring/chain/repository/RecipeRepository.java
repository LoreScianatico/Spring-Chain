package com.lorescianatico.spring.chain.repository;

import com.lorescianatico.spring.chain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByName(String name);

    List<Recipe> findByNameStartsWith(String name);

}
