package com.tasty.recipesapp.repository.recipe.model

data class RecipeModel (
    val id: Int,
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