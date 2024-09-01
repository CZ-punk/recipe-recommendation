package com.ch.back.recipe.repository;

import com.ch.back.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByUsername(String username);

}
