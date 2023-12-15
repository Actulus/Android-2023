package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeDetailViewModel

private const val ARG_RECIPE_ID = "recipeID"
private val TAG: String? = RecipeRepository::class.java.canonicalName
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RecipeDetailFragment : Fragment(R.layout.fragment_recipe_detail) {
    private var param1: String? = null
    private var param2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeID = arguments?.getLong(ARG_RECIPE_ID)
        Log.d(TAG, "Recipe ID: $recipeID")

        val viewModel = ViewModelProvider(this)[RecipeDetailViewModel::class.java]
        recipeID?.let {
            viewModel.fetchRecipeData(it)
        }

        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            Log.d(TAG, "Recipe observed: $recipe")
            recipe?.let { updateViews(it) }
        }
    }

    private fun updateViews(recipe: RecipeModel) {
        Log.d(TAG, "RecipeDetailFragment: updateViews")
        Log.d(TAG, "Recipe: $recipe")

        val recipeNameTextView: TextView = requireView().findViewById(R.id.recipeName)
        val recipeDescriptionTextView: TextView = requireView().findViewById(R.id.recipeDescription)
        val recipeIngredientsTextView: TextView = requireView().findViewById(R.id.recipeIngredients)
        val recipeStepsTextView: TextView = requireView().findViewById(R.id.recipeInstructions)
        val recipeImageUrlImageView: ImageView = requireView().findViewById(R.id.recipeImage)

        recipeNameTextView.text = recipe.name
        recipeDescriptionTextView.text = recipe.description

        recipeIngredientsTextView.text = buildString {
            for (ingredient in recipe.ingredients) {
                append("- ${ingredient.name}: ${ingredient.quantity} ${ingredient.unit}")
            }
        }

        recipeStepsTextView.text = buildString {
            for (instruction in recipe.instructions){
                append("${instruction}\n")
            }
        }

        recipe.imageUrl?.let {
            recipeImageUrlImageView.load(it) {
                crossfade(true) // Optional: Enable crossfade for smooth image transitions
                placeholder(R.drawable.ic_launcher_background) // Optional: Set a placeholder image while loading
            }
        }
    }
}