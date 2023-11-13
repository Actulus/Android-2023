package com.tasty.recipesapp.repository.recipe

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.tasty.recipesapp.repository.recipe.model.RecipeDTO
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipesDTO
import com.tasty.recipesapp.repository.recipe.model.toModel
import java.io.IOException

object RecipeRepository {
    private val TAG: String? = RecipeRepository::class.java.canonicalName

    fun getRecipes(context: Context): List<RecipeModel> {
        lateinit var jsonString: String

        try {
            val assetManager = context.assets
            val files = assetManager.list("") // Lists files/directories in the assets folder
            Log.d(TAG, "List of files in assets: ${files?.joinToString()}")

            if (files != null && files.contains("new_recipes.json")) {
                // File exists, proceed with reading
                jsonString = assetManager.open("new_recipes.json").bufferedReader().use { it.readText() }
                val gson = Gson()
                val recipesResponse: RecipesDTO = gson.fromJson(jsonString, RecipesDTO::class.java)
                return recipesResponse.results.map { it.toModel() }
            } else {
                Log.e(TAG, "File 'new_recipes.json' not found in assets folder.")
                return emptyList()
            }
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, "Error parsing JSON: $e")
        } catch (e: IOException) {
            Log.e(TAG, "Error reading JSON file: $e")
        } catch (e: Exception) {
            Log.e(TAG, "Unknown error: $e")
        }

        val recipesResponse: RecipesDTO = Gson().fromJson(jsonString, RecipesDTO::class.java)
        return recipesResponse.results.map { it.toModel() }
        /*return emptyList()*/
    }

}