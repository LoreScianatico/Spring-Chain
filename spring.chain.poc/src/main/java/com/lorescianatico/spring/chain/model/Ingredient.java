package com.lorescianatico.spring.chain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "INGREDIENTS", indexes = {@Index(name="INGREDIENTS_NAME_IDX", columnList = "NAME")})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class Ingredient extends AbstractEntity {

    @NotNull
    @NonNull
    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    private Recipe recipe;

}
