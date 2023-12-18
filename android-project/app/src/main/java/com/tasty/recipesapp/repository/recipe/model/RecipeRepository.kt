package com.tasty.recipesapp.repository.recipe.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

object RecipeRepository {
    private val TAG: String? = RecipeRepository::class.java.canonicalName
    private var recipesList: List<RecipeModel> = emptyList()
    private val newRecipes = mutableListOf<RecipeModel>()
    private const val BACKEND_URL = "http://192.168.43.194:5000" // Replace with your own IP

    data class SaveRecipeCreationResponse(val recipe_id: Long)
    data class UpdateRecipeResponse(val recipe_id: Long)

    private interface RecipeApiService {
        @GET("recipes") // Define the endpoint for getting recipes
        suspend fun getRecipes(): RecipesDTO

        @GET("recipes/{id}")
        suspend fun getRecipeById(@Path("id") id: Long): RecipeDTO

        @POST("recipes")
        suspend fun saveRecipe(@Body recipe: RecipeDTO): Response<SaveRecipeCreationResponse>

        @DELETE("recipes/{id}")
        suspend fun deleteRecipe(@Path("id") id: Long): Response<Unit>

        @PUT("recipes/{id}")
        suspend fun updateRecipe(@Path("id") id: Long, @Body recipe: RecipeDTO): Response<UpdateRecipeResponse>
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BACKEND_URL) // Corrected the base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val recipeApiService = retrofit.create(RecipeApiService::class.java)

    suspend fun getRecipes(context: Context): List<RecipeModel> {
/*        lateinit var jsonString: String;

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

        return recipesResponse.results.map { it.toModel() }*/

        try {
            val recipesResponse = recipeApiService.getRecipes()
            Log.d(TAG, "recipesResponse: $recipesResponse")
            // Save the list of recipes into a variable
            recipesList = recipesResponse.results.map { it.toModel() }

            Log.d(TAG, "recipesList: $recipesList")

            // Convert DTOs into domain models
            return recipesList
        } catch (e: Exception) {
            Log.e(TAG, "Error reading JSON file: $e")
            return emptyList()
        }
    }

    fun getRecipe(recipeID: Long): RecipeModel? {
        if (recipeID == 0L) {
            val lastRecipe = newRecipes.lastOrNull()
            if (lastRecipe == null) {
                Log.e(TAG, "Recipe is null.")
            } else {
                Log.d(TAG, "Recipe: $lastRecipe")
            }
            return lastRecipe
        } else {
            val recipe = recipesList.find { it.recipeID == recipeID }
            if (recipe == null) {
                Log.e(TAG, "Recipe is null.")
            } else {
                Log.d(TAG, "Recipe: $recipe")
            }
            return recipe
        }
    }

    suspend fun getRecipeById(id: Long): RecipeModel? {
        try {
            // Make the GET request using Retrofit
            val recipeResponse = recipeApiService.getRecipeById(id)
            Log.d(TAG, "recipeResponse: $recipeResponse")
            // Convert DTO into domain model
            return recipeResponse.toModel()
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Log.d(TAG, "Recipe not found with ID: $id")
                return null
            } else {
                Log.e(TAG, "Error while fetching recipe: $e")
                return null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error while fetching recipe: $e")
            return null
        }
    }

    suspend fun saveRecipe(recipe: RecipeModel): Long? {
        try {
            // Convert domain model into DTO
            val recipeDTO = recipe.toDTO()

            // Make the POST request using Retrofit
            val response = recipeApiService.saveRecipe(recipeDTO)

            // Check if the request was successful
            if (response.isSuccessful) {
                // Get the recipeID from the API response
                return response.body()?.recipe_id
            } else {
                Log.d(TAG, "Error while saving recipe: ${response.errorBody()?.string()}")
                return null
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while saving recipe: $e")
            return null
        }
    }

    suspend fun saveRecipeToJson(recipeModel: RecipeModel, context: Context) {
        try {
            // Convert only the new recipe to JSON
            val gson = Gson()
            val newRecipeJson = gson.toJson(recipeModel)

            // Append the new recipe JSON to the file
            context.openFileOutput("new_recipes.json", Context.MODE_APPEND).use {
                it.write(newRecipeJson.toByteArray())
                it.write(System.lineSeparator().toByteArray()) // Add a newline for separation
            }

            val recipes = RecipeRepository.getRecipes(context)
            Log.d("NewRecipesFragment", "Recipes from Repository: $recipes")

        } catch (e: IOException) {
            Log.e("NewRecipesFragment", "Error writing to new_recipes.json: $e")
        }
    }

    suspend fun deleteRecipe(recipeId: Long): Boolean{
        try {
            // Make the DELETE request using Retrofit
            val response = recipeApiService.deleteRecipe(recipeId)

            // Check if the request was successful
            if (response.isSuccessful) {
                return true
            } else {
                Log.d(TAG, "Error while deleting recipe: ${response.errorBody()?.string()}")
                return false
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while deleting recipe: $e")
            return false
        }
    }

    suspend fun updateRecipe(recipe: RecipeModel): Long? {
        try {
            // Convert domain model into DTO
            val recipeDTO = recipe.toDTO()

            // Make the PUT request using Retrofit
            val response = recipeApiService.updateRecipe(recipe.recipeID, recipeDTO)

            // Check if the request was successful
            if (response.isSuccessful) {
                return response.body()?.recipe_id
            } else {
                Log.d(TAG, "Error while updating recipe: ${response.errorBody()?.string()}")
                return null
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while updating recipe: $e")
            return null
        }
    }

    fun searchRecipe(query: String?): List<RecipeModel>? {
        return if (query.isNullOrBlank()) {
            recipesList
        } else {
            recipesList.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}