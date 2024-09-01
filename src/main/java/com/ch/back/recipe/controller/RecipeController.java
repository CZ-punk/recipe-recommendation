package com.ch.back.recipe.controller;

import com.ch.back.basic_setting.jwt.JwtUtils;
import com.ch.back.basic_setting.jwt.UserDetailsImpl;
import com.ch.back.recipe.controller.request.RecipeDto;
import com.ch.back.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "Recipe Controller")
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/create")
    public String recipeHome() {
        return "recipe";
    }

    @PostMapping
    public String createRecipe(@RequestBody RecipeDto recipeDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        recipeService.createRecipe(recipeDto, userDetails.getUsername());
        return "redirect:/";  // 메인 페이지로 리다이렉트
    }

    // 레시피 ID 에 대한 상세 내용 조회
//    @GetMapping("/")

    // user 의 레시피를 보여줌
    @GetMapping("/user/{username}")
    public String getRecipeListByUsername(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        recipeService.getRecipeListByUsername(username)
        return null;
    }




    // 관리자용
    @GetMapping("/user")
    public String getRecipeList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return null;
    }
}
