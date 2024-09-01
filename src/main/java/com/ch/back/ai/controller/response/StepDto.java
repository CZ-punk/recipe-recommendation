package com.ch.back.ai.controller.response;

import com.ch.back.recipe.domain.Step;

public record StepDto(
        int order,
        String description
) {

    public static StepDto of(Step step) {
        return new StepDto(step.getOrder(), step.getDescription());
    }

}
