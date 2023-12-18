// EditRecipeViewModel.kt
package com.tasty.recipesapp.ui.recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import kotlinx.coroutines.launch

class EditRecipeViewModel : ViewModel() {
    private val _recipe = MutableLiveData<RecipeModel?>()
    val recipe: LiveData<RecipeModel?> get() = _recipe

    private val _recipeSaved = MutableLiveData<Long?>()
    val recipeSaved: LiveData<Long?> get() = _recipeSaved

    private val recipeRepository = RecipeRepository

    fun saveRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                // Save the edited recipe using the repository
                val updatedRecipe = recipeRepository.updateRecipe(recipe)

                // Assuming the update is successful, set the value to true
                _recipeSaved.value = updatedRecipe
            } catch (e: Exception) {
                // Handle the error, log, or show a message to the user
                _recipeSaved.value = null
            }
        }
    }

    fun fetchRecipeById(recipeID: Long) {
        viewModelScope.launch {
            try {
                // Fetch the recipe using the repository
                val recipe = recipeRepository.getRecipeById(recipeID)

                // Assuming the fetch is successful, set the value to the recipe
                _recipe.value = recipe
            } catch (e: Exception) {
                // Handle the error, log, or show a message to the user
                _recipe.value = null
            }
        }
    }
}
