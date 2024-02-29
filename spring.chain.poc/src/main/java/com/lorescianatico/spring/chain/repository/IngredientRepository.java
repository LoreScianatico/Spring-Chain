package com.lorescianatico.spring.chain.repository;

import com.lorescianatico.spring.chain.model.Ingredient;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
