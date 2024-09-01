package com.ch.back.recipe.domain;

import com.ch.back.recipe.controller.request.RecipeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Recipe {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String username;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(RecipeDto recipeDto, String username) {
        ingredients.addAll(recipeDto.getIngredients()
                .stream()
                .map(ingredient -> new Ingredient(ingredient, this))
                .toList());

        this.title = recipeDto.getRecipeName();
        this.username = username;
    }
}
