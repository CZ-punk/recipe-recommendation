package com.ch.back.ai.controller.response;

import com.ch.back.recipe.controller.response.IngredientDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public record ClientResponse(
        String recipeName,
        List<String> ingredients,
        List<StepDto> steps
) {
    public static ClientResponse fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, ClientResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 파싱 오류", e);
        }
    }

}

