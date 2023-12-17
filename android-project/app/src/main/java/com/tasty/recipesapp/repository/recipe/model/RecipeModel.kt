package com.tasty.recipesapp.repository.recipe.model

data class RecipeModel (
    val recipeID: Long,
    val name: String,
    val description: String,
    val ingredients: List<IngredientModel>,
    val instructions: List<String>,
    val imageUrl: String? = null,
) {
    fun toDTO(): RecipeDTO {
        return RecipeDTO(
            id = recipeID,
            name = name,
            description = description,
            ingredients = ingredients.map { it.toDTO() },
            instructions = instructions,
            imageUrl = imageUrl
        )
    }
}

data class IngredientModel(
    val name: String,
    val quantity: Double,
    val unit: String,
){
    fun toDTO(): IngredientDTO {
        return IngredientDTO(
            name = name,
            quantity = quantity,
            unit = unit
        )
    }
}