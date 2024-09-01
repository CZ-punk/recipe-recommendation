package com.ch.back.recipe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ingredient {

    @Id @GeneratedValue
    private Long id;

    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Ingredient(String itemName, Recipe recipe) {
        this.itemName = itemName;
        this.recipe = recipe;
    }

}
