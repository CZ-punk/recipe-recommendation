package com.ch.back.recipe.controller.response;

import com.ch.back.recipe.domain.Ingredient;

public record IngredientDto(String ingredientName) {

    public static IngredientDto of(Ingredient ingredient) {
        return new IngredientDto(ingredient.getItemName());
    }
}
