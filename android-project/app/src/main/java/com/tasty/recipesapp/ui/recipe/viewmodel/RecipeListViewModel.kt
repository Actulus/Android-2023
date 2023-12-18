package com.tasty.recipesapp.ui.recipe.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import kotlinx.coroutines.launch

class RecipeListViewModel: ViewModel() {
    private val TAG = RecipeListViewModel::class.java.canonicalName
    private val repository = RecipeRepository

    val recipesList: MutableLiveData<List<RecipeModel>> = MutableLiveData()

    fun fetchRecipeData(context: Context){
        viewModelScope.launch { recipesList.value = repository.getRecipes(context) }
    }

    fun deleteRecipe(deletedRecipe: RecipeModel) {
        viewModelScope.launch {
            val deletedSuccessfully = repository.deleteRecipe(deletedRecipe.recipeID)
            if (deletedSuccessfully) {
                val currentRecipes = recipesList.value.orEmpty().toMutableList()
                currentRecipes.remove(deletedRecipe)
                recipesList.value = currentRecipes
            }
        }
    }

    fun searchRecipe(query: String?) {
        viewModelScope.launch {
            recipesList.value = repository.searchRecipe(query)
        }
    }
}