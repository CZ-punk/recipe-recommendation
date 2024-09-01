package com.ch.back.recipe.controller.request;


import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

    private String recipeName;
    private List<String> ingredients;
}
