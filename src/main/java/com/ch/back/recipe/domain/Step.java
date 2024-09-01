package com.ch.back.recipe.domain;


import com.ch.back.ai.controller.response.StepDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "step_order")
    private int order;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Step(StepDto stepDto, Recipe recipe) {
        this.order = stepDto.order();
        this.description = stepDto.description();
        this.recipe = recipe;
    }
}
