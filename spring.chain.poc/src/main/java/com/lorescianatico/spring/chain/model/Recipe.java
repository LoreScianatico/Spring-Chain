package com.lorescianatico.spring.chain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RECIPES", indexes = {@Index(name="RECIPES_NAME_IDX", columnList = "NAME")})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe extends AbstractEntity{

    @NotNull
    @NonNull
    @Column(name = "NAME", unique = true)
    private String name;

    @Lob
    private String directions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
}
