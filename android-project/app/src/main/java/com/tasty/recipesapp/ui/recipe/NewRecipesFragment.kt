package com.tasty.recipesapp.ui.recipe

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.model.IngredientModel
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.ui.recipe.viewmodel.NewRecipeViewModel
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeDetailViewModel
import okio.IOException

class NewRecipesFragment: Fragment(R.layout.new_recipe) {
    private val layoutIngredients by lazy { view?.findViewById<LinearLayout>(R.id.layoutIngredients) }
    private val layoutInstructions by lazy { view?.findViewById<LinearLayout>(R.id.layoutInstructions) }
    private val viewModel by lazy { ViewModelProvider(this)[NewRecipeViewModel::class.java] }
    private val newRecipes = mutableListOf<RecipeModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addIngredientButton = view.findViewById<View>(R.id.btnAddIngredient)
        addIngredientButton.setOnClickListener {
            addIngredientTextField()
        }

        val addInstructionButton = view.findViewById<View>(R.id.btnAddInstruction)
        addInstructionButton.setOnClickListener {
            addInstructionTextField()
        }

        val saveRecipeButton = view.findViewById<View>(R.id.btnSave)
        saveRecipeButton.setOnClickListener {
            if (validateInput()) {
                saveRecipeData()
            }
        }

        viewModel.recipeSaved.observe(viewLifecycleOwner) { recipeID ->
            if (recipeID != null) {
                Log.d("NewRecipesFragment", "Recipe: $recipeID")
                findNavController().navigate(R.id.action_recipeFormFragment_to_recipeDetailFragment, bundleOf("recipeID" to recipeID))
            } else {
                Log.e("NewRecipesFragment", "Recipe is null.")
            }
        }
    }

    private fun addIngredientTextField() {
        val textInputLayout = TextInputLayout(requireContext())
        val editText = TextInputEditText(requireContext())
        editText.hint = "Enter ingredient"
        editText.setTextColor(Color.WHITE)
        textInputLayout.addView(editText)

        layoutIngredients?.addView(textInputLayout)
    }
    private fun addInstructionTextField() {
        val textInputLayout = TextInputLayout(requireContext())
        val editText = TextInputEditText(requireContext())
        editText.hint = "Enter instruction"
        editText.setTextColor(Color.WHITE)
        textInputLayout.addView(editText)

        layoutInstructions?.addView(textInputLayout)
    }

    private fun saveRecipeData() {
        val recipeName = view?.findViewById<TextInputEditText>(R.id.editTextRecipeName)?.text?.toString()
        val recipeDescription = view?.findViewById<TextInputEditText>(R.id.editTextRecipeDescription)?.text?.toString()
        val recipeImageUrl = view?.findViewById<TextInputEditText>(R.id.editTextRecipeImageUrl)?.text?.toString()

        val ingredientsList = mutableListOf<IngredientModel>()
        for (i in 0 until layoutIngredients?.childCount!!) {
            val ingredientEditText = layoutIngredients?.getChildAt(i) as? TextInputLayout
            val ingredientText = ingredientEditText?.editText?.text?.toString()
            if (!ingredientText.isNullOrBlank()) {
                val ingredientModel = IngredientModel(name = ingredientText, quantity = 0.0, unit = "")
                ingredientsList.add(ingredientModel)
            }
        }

        val instructionsList = mutableListOf<String>()
        for (i in 0 until layoutInstructions?.childCount!!) {
            val instructionEditText = layoutInstructions?.getChildAt(i) as? TextInputLayout
            val instructionText = instructionEditText?.editText?.text?.toString()
            if (!instructionText.isNullOrBlank()) {
                instructionsList.add(instructionText)
            }
        }


        // Create RecipeModel instance
        val recipeModel = RecipeModel(
            recipeID = -1, // Set the recipeID to -1 to indicate that it is a new recipe
            name = recipeName.orEmpty(),
            description = recipeDescription.orEmpty(),
            ingredients = ingredientsList,
            instructions = instructionsList,
            imageUrl = recipeImageUrl
        )

        // Save the recipe to the database using ViewModel
        newRecipes.add(recipeModel)
//        saveRecipeToJson(recipeModel)

        viewModel.saveRecipe(recipeModel)

        // Now you can use the recipeModel as needed, for example, save it to the database.
        // Adjust the storage mechanism according to your requirements.
        Log.d("NewRecipesFragment", "Recipe saved: $recipeModel")

//        navigateToCreatedRecipe(recipeModel.recipeID)

        // Navigate to the RecipeDetailFragment or any other destination
//        findNavController().navigate(R.id.action_recipeFormFragment_to_recipeDetailFragment)
    }

    /*private fun saveRecipeToJson(recipeModel: RecipeModel) {
        try {
            // Convert only the new recipe to JSON
            val gson = Gson()
            val newRecipeJson = gson.toJson(recipeModel)

            // Append the new recipe JSON to the file
            requireContext().openFileOutput("new_recipes.json", Context.MODE_APPEND).use {
                it.write(newRecipeJson.toByteArray())
                it.write(System.lineSeparator().toByteArray()) // Add a newline for separation
            }

            val recipes = RecipeRepository.getRecipes(requireContext())
            Log.d("NewRecipesFragment", "Recipes from Repository: $recipes")

        } catch (e: IOException) {
            Log.e("NewRecipesFragment", "Error writing to new_recipes.json: $e")
        }
    }*/

    private fun validateInput(): Boolean {
        val recipeName = view?.findViewById<TextInputEditText>(R.id.editTextRecipeName)?.text?.toString()
        val recipeDescription = view?.findViewById<TextInputEditText>(R.id.editTextRecipeDescription)?.text?.toString()
        val recipeImageUrl = view?.findViewById<TextInputEditText>(R.id.editTextRecipeImageUrl)?.text?.toString()

        if (recipeName.isNullOrBlank()) {
            view?.findViewById<TextInputLayout>(R.id.textInputLayoutRecipeName)?.error = "Recipe name is required"
            showToast("Recipe name is required")
            return false
        }

        if (recipeDescription.isNullOrBlank()) {
            view?.findViewById<TextInputLayout>(R.id.textInputLayoutRecipeDescription)?.error = "Recipe description is required"
            showToast("Recipe description is required")
            return false
        }

        if (recipeImageUrl.isNullOrBlank()) {
            view?.findViewById<TextInputLayout>(R.id.textInputLayoutRecipeImageUrl)?.error = "Recipe image URL is required"
            showToast("Recipe image URL is required")
            return false
        }

        val ingredients = mutableListOf<IngredientModel>()
        for (i in 0 until layoutIngredients?.childCount!!) {
            val ingredientEditText = layoutIngredients?.getChildAt(i) as? TextInputLayout
            val ingredientText = ingredientEditText?.editText?.text?.toString()
            if (!ingredientText.isNullOrBlank()) {
                val ingredientModel = IngredientModel(name = ingredientText, quantity = 0.0, unit = "")
                ingredients.add(ingredientModel)
            }
        }

        if (ingredients.isEmpty()) {
            view?.findViewById<TextInputLayout>(R.id.recipeIngredients)?.error = "At least one ingredient is required"
            showToast("At least one ingredient is required")
            return false
        }

        val instructions = mutableListOf<String>()
        for (i in 0 until layoutInstructions?.childCount!!) {
            val instructionEditText = layoutInstructions?.getChildAt(i) as? TextInputLayout
            val instructionText = instructionEditText?.editText?.text?.toString()
            if (!instructionText.isNullOrBlank()) {
                instructions.add(instructionText)
            }
        }

        if (instructions.isEmpty()) {
            view?.findViewById<TextInputLayout>(R.id.recipeInstructions)?.error = "At least one instruction is required"
            showToast("At least one instruction is required")
            return false
        }

        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCreatedRecipe(recipeID: Long) {
        findNavController().navigate(R.id.action_recipeFormFragment_to_recipeDetailFragment, bundleOf("recipeID" to recipeID))
    }

}