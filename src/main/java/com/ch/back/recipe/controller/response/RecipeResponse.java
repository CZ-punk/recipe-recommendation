package com.ch.back.recipe.controller.response;

import com.ch.back.ai.controller.response.StepDto;
import com.ch.back.recipe.domain.Ingredient;
import com.ch.back.recipe.domain.Recipe;

import java.util.List;

public record RecipeResponse(String recipeName, String username, List<IngredientDto> ingredients, List<StepDto> steps) {

    public static RecipeResponse of(Recipe recipe) {
        return new RecipeResponse(recipe.getTitle(), recipe.getUsername(), recipe.getIngredientDto(), recipe.getStepDto());
    }
}
