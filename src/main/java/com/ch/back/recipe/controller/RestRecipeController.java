package com.ch.back.recipe.controller;

import com.ch.back.jwt.UserDetailsImpl;
import com.ch.back.recipe.controller.request.RecipeRequest;
import com.ch.back.recipe.controller.response.RecipeResponse;
import com.ch.back.recipe.service.RecipeService;
import com.ch.back.support.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "Rest Recipe Controller")
@RequestMapping("/api/recipe")
public class RestRecipeController {

    private final RecipeService recipeService;


    @PostMapping
    public ApiResponse<?> createRecipe(@RequestBody RecipeRequest recipeRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("/create/recipe 호출! {}", recipeRequest);
        recipeService.createRecipe(recipeRequest, userDetails.getUsername());
        return ApiResponse.success();
    }

    @PostMapping("/user/{username}")
    public ApiResponse<?> getRecipeListByUsername(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<RecipeResponse> data = recipeService.getRecipeListByUsername(username);
        log.info("getRecipeListByUsername: {}", data);
        return ApiResponse.success(data);
    }


    // 관리자용
//    @GetMapping("/user")
//    public String getRecipeList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return null;
//    }
}
