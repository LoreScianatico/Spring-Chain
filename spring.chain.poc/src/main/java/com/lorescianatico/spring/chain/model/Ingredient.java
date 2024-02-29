package com.lorescianatico.spring.chain.model;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "INGREDIENTS", indexes = {@Index(name="INGREDIENTS_NAME_IDX", columnList = "NAME")})
@Data
@EqualsAndHashCode(of = {"name"}, callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient extends AbstractEntity {

    @NotNull
    @NonNull
    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Recipe> recipe = new HashSet<>();

    public void addRecipe(Recipe recipe){
        this.recipe.add(recipe);
        recipe.getIngredients().add(this);
    }

}
