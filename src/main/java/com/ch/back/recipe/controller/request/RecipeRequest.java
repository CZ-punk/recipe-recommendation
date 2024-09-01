package com.ch.back.recipe.controller.request;


import com.ch.back.ai.controller.response.StepDto;
import com.ch.back.recipe.controller.response.IngredientDto;

import java.util.List;


public record RecipeRequest(
        String recipeName,
        List<String> ingredients,
        List<StepDto> steps
) {
}
