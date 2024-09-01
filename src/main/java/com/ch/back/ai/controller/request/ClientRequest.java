package com.ch.back.ai.controller.request;

import com.ch.back.recipe.controller.response.IngredientDto;

import java.util.List;

public record ClientRequest(String recipeName, List<IngredientDto> ingredients, String question) {
}
