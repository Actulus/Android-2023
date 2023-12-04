package com.tasty.recipesapp.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
//import com.tasty.recipesapp.ui.recipe.RecipesFragment.Companion.BUNDLE_EXTRA_SELECTED_RECIPE_ID

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        findNavController().navigate(R.id.action_recipesFragment_to_recipeDetailFragment,
//            bundleOf(BUNDLE_EXTRA_SELECTED_RECIPE_ID to recipe.id))
            bundleOf("recipeID" to recipe.recipeID))
    }

}