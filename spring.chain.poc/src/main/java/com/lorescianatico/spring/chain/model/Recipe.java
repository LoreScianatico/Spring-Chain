package com.lorescianatico.spring.chain.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RECIPES", indexes = {@Index(name="RECIPES_NAME_IDX", columnList = "NAME")})
@Data
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe extends AbstractEntity{

    @NotNull
    @NonNull
    @Column(name = "NAME", unique = true)
    private String name;

    @Lob
    @Column(name = "DIRECTIONS")
    private String directions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
        ingredient.getRecipe().add(this);
    }
}
