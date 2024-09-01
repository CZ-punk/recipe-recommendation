package com.ch.back.recipe.service;

import com.ch.back.recipe.controller.request.RecipeRequest;
import com.ch.back.recipe.controller.response.RecipeResponse;
import com.ch.back.recipe.domain.Recipe;
import com.ch.back.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public void createRecipe(RecipeRequest recipeRequest, String username) {
        recipeRepository.save(new Recipe(recipeRequest).addUsername(username));
    }

    public List<RecipeResponse> getRecipeListByUsername(String username) {
        List<Recipe> recipeList = recipeRepository.findAllByUsername(username);
        return recipeList.stream().map(RecipeResponse::of).toList();
    }
}
