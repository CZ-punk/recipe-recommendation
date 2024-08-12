package com.ch.back.dto;


import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

    private List<String> ingredients;
}
