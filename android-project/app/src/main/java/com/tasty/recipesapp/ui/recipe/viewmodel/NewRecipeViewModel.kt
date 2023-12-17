package com.tasty.recipesapp.ui.recipe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import kotlinx.coroutines.launch

class NewRecipeViewModel: ViewModel() {
    private val _recipeSaved = MutableLiveData<Long?>()
    val recipeSaved: LiveData<Long?> get() = _recipeSaved

    // You can inject the repository or use dependency injection framework like Dagger
    private val recipeRepository = RecipeRepository

    fun saveRecipe(recipe: RecipeModel) {
        viewModelScope.launch {
            try {
                // Save the recipe using the repository
                val recipeID = recipeRepository.saveRecipe(recipe)

                // Assuming the save is successful, set the value to the id
                _recipeSaved.value = recipeID
            } catch (e: Exception) {
                // Handle the error, log, or show a message to the user
                _recipeSaved.value = null
            }
        }
    }
}