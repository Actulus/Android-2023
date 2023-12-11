package com.tasty.recipesapp.repository.recipe.model

import com.tasty.recipesapp.repository.recipe.model.RecipesDTO

data class RecipesDTO (
    val count: Int,
    val results : List<RecipeDTO>
)


