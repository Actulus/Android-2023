package com.tasty.recipesapp.repository.recipe.model

data class RecipeDTO(
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<IngredientDTO>,
    val instructions: List<String>,
    val imageUrl: String? = null,
)

data class IngredientDTO(
    val name: String,
    val quantity: Double,
    val unit: String,
)
fun IngredientDTO.toModel() = IngredientModel(
    name = name,
    quantity = quantity,
    unit = unit,
)

fun RecipeDTO.toModel() = RecipeModel (
        id = this.id,
        name = this.name,
        description = this.description,
        ingredients = this.ingredients.map { it.toModel() },
        instructions = this.instructions,
        imageUrl = this.imageUrl
)

/*fun List<RecipeDTO>.toModelList(): List<RecipeModel> = this.map { it.toModel() }*/


