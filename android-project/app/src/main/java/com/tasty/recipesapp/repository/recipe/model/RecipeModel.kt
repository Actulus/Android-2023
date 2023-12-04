package com.tasty.recipesapp.repository.recipe.model

data class RecipeModel (
    val recipeID: Long,
    val name: String,
    val description: String,
    val ingredients: List<IngredientModel>,
    val instructions: List<String>,
    val imageUrl: String? = null,
)

data class IngredientModel(
    val name: String,
    val quantity: Double,
    val unit: String,
)