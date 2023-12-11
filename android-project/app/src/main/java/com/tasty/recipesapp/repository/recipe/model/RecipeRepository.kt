package com.tasty.recipesapp.repository.recipe.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException
import com.tasty.recipesapp.repository.recipe.model.RecipesDTO

object RecipeRepository {
    private val TAG: String? = RecipeRepository::class.java.canonicalName
    private var recipesList: List<RecipeModel> = emptyList()

    fun getRecipes(context: Context): List<RecipeModel> {
        lateinit var jsonString: String;

        try {
            jsonString = context.assets.open("new_recipes.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val recipesDTOs = gson.fromJson(jsonString, Array<RecipeDTO>::class.java).toList()
            return recipesDTOs.map {it.toModel()}
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing JSON: $e")
        } catch (e: IOException) {
            Log.e(TAG, "Error reading JSON file: $e")
        } catch (e: Exception) {
            Log.e(TAG, "Unknown error: $e")
        }

        val recipesResponse: RecipesDTO = Gson().fromJson(jsonString, RecipesDTO::class.java)
        this.recipesList = recipesResponse.results.map { it.toModel() }

        if (this.recipesList.isEmpty()) {
            Log.e(TAG, "RecipeList is empty.")
        } else {
            Log.d(TAG, "RecipeList: $recipesList")
        }

        return recipesResponse.results.map { it.toModel() }
    }

    fun getRecipe(recipeID: Long): RecipeModel? {
        val recipe = recipesList.find { it.recipeID == recipeID }
        if (recipe == null) {
            Log.e(TAG, "Recipe is null.")
        } else {
            Log.d(TAG, "Recipe: $recipe")
        }
        return recipe
    }
}