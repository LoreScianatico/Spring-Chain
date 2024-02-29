package com.lorescianatico.spring.chain.repository;

import com.lorescianatico.spring.chain.model.Recipe;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Lazy //this avoids unintended circular dependencies
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findByName(String name);

    List<Recipe> findByNameStartsWith(String name);

}
