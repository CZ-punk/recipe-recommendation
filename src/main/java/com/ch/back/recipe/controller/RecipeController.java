package com.ch.back.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j(topic = "Recipe Controller")
@RequestMapping("/api/recipe")
public class RecipeController {

    @GetMapping("/create")
    public String createRecipe() {
        return "recipe/create";
    }

    @GetMapping("/user/{username}")
    public String myRecipePage(@PathVariable String username, Model model) {
        model.addAttribute("username", username);
        return "recipe/myRecipePage";
    }

}
