package com.tasty.recipesapp.ui.recipe.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.RecipeRepository

class RecipeListViewModel: ViewModel() {
    private val repository = RecipeRepository

    val recipeList: MutableLiveData<List<RecipeModel>> = MutableLiveData()

    fun fetchRecipeData(context: Context){
        val recipes = repository.getRecipes(context)
        recipeList.value = recipes
    }
}