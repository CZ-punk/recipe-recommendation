package com.ch.back.controller;

import com.ch.back.dto.RecipeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "Recipe Controller")
@RequestMapping("/api")
public class RecipeController {

    @GetMapping("/recipe_home")
    public String recipeHome() {
        return "recipe";
    }

    @PostMapping("/recipe")
    public String createRecipe(@RequestBody RecipeDto recipeDto) {
        List<String> ingredients = recipeDto.getIngredients();
        log.info("ingredients: {}", ingredients);
        return "index";
    }
}
