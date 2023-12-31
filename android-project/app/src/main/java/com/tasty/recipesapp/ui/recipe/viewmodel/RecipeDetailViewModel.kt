package com.tasty.recipesapp.ui.recipe.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import kotlinx.coroutines.launch

class RecipeDetailViewModel: ViewModel() {
    private val TAG = RecipeDetailViewModel::class.java.canonicalName
    var recipe: MutableLiveData<RecipeModel> = MutableLiveData()

    fun fetchRecipeData(recipeID: Long) {
        /*val recipe = RecipeRepository.getRecipe(recipeID)
        if (recipe == null) {
            Log.e(TAG, "Recipe is null.")
        } else {
            Log.d(TAG, "Recipe: $recipe")
            this.recipe.value = recipe
        }*/
        viewModelScope.launch{
            recipe.value = RecipeRepository.getRecipeById(recipeID)
        }
    }
}