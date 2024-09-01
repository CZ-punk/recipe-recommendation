package com.ch.back.recipe.service;

import com.ch.back.recipe.controller.request.RecipeDto;
import com.ch.back.recipe.domain.Ingredient;
import com.ch.back.recipe.domain.Recipe;
import com.ch.back.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public void createRecipe(RecipeDto recipeDto, String username) {
        recipeRepository.save(new Recipe(recipeDto, username));
    }
}
