package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.RecipeRepository
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeListViewModel

class RecipesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)

        context?.let {
            viewModel.fetchRecipeData(it)
        }

        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            for (recipe in recipes) {
                Log.d("RecipesFragment", "Recipe: $recipe")
            }
        }
    }
}