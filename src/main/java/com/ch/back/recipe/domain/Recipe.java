package com.ch.back.recipe.domain;

import com.ch.back.ai.controller.response.StepDto;
import com.ch.back.recipe.controller.request.RecipeRequest;
import com.ch.back.recipe.controller.response.IngredientDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Slf4j
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @OrderBy("order")
    private List<Step> steps = new ArrayList<>();

    public Recipe(RecipeRequest recipeRequest) {
        this.title = recipeRequest.recipeName();
        this.ingredients = recipeRequest.ingredients().stream().map(ingredient -> new Ingredient(ingredient, this)).toList();
        this.steps = recipeRequest.steps().stream().map(stepDto -> new Step(stepDto, this)).toList();
    }

    public Recipe addUsername(String username) {
        this.username = username;
        return this;
    }

    public List<IngredientDto> getIngredientDto() {
        return ingredients.stream().map(IngredientDto::of).toList();
    }

    public List<StepDto> getStepDto() {
        return steps.stream().map(StepDto::of).toList();
    }
}
