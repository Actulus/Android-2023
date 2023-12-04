package com.tasty.recipesapp.repository.recipe.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException

object RecipeRepository {
    private val TAG: String? = RecipeRepository::class.java.canonicalName
    private var recipeList: List<RecipeModel> = emptyList()

    fun getRecipes(context: Context): List<RecipeModel> {
        var jsonString: String = ""

        try {
            val assetManager = context.assets
            val files = assetManager.list("") // Lists files/directories in the assets folder
            Log.d(TAG, "List of files in assets: ${files?.joinToString()}")

            return if (files != null && files.contains("new_recipes.json")) {
                // File exists, proceed with reading
                jsonString = assetManager.open("new_recipes.json").bufferedReader().use { it.readText() }
                val gson = Gson()
                val recipesResponse: RecipesDTO = gson.fromJson(jsonString, RecipesDTO::class.java)
                Log.d(TAG, "RecipesDTO: $recipesResponse")
                recipesResponse.results.map { it.toModel() }
            } else {
                Log.e(TAG, "File 'new_recipes.json' not found in assets folder.")
                emptyList()
            }
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing JSON: $e")
        } catch (e: IOException) {
            Log.e(TAG, "Error reading JSON file: $e")
        } catch (e: Exception) {
            Log.e(TAG, "Unknown error: $e")
        }

        Log.d(TAG, "JSON String: $jsonString")
        val recipesResponse: RecipesDTO = Gson().fromJson(jsonString, RecipesDTO::class.java)
        Log.d(TAG, "RecipesDTO: $recipesResponse")
        this.recipeList = recipesResponse.results.map { it.toModel() }
        Log.d(TAG, "RecipeList0: ${recipeList.size}")

        if (this.recipeList.isEmpty()) {
            Log.e(TAG, "RecipeList is empty.")
        } else {
            Log.d(TAG, "RecipeList: $recipeList")
        }
        Log.d(TAG, "RecipeList1: ${this.recipeList.size}")
//        recipesResponse.results.map { it.toModel() }
        return this.recipeList
    }

    fun getRecipe(recipeID: Long): RecipeModel? {
        Log.d(TAG, "Get RecipeID: $recipeID")
        Log.d(TAG, "RecipeList2: ${recipeList.size}")
        val recipe = recipeList.find { it.recipeID == recipeID }
        if (recipe == null) {
            Log.e(TAG, "Recipe is null.")
        } else {
            Log.d(TAG, "Recipe: $recipe")
        }
        return recipe
    }

}