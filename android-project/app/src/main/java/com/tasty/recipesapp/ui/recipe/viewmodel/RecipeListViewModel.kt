package com.tasty.recipesapp.ui.recipe.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeListViewModel: ViewModel() {
    private val TAG = RecipeListViewModel::class.java.canonicalName
    private val repository = RecipeRepository

    val recipesList: MutableLiveData<List<RecipeModel>> = MutableLiveData()

    fun fetchRecipeData(context: Context){
        recipesList.value = repository.getRecipes(context = context)
    }
}