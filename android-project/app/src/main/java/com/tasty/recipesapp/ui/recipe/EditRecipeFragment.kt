// EditRecipeFragment.kt
package com.tasty.recipesapp.ui.recipe

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
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.model.IngredientModel
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.ui.recipe.viewmodel.EditRecipeViewModel

private val TAG: String? = EditRecipeViewModel::class.java.canonicalName
class EditRecipeFragment : Fragment(R.layout.edit_recipe) {
    private val layoutIngredients by lazy { view?.findViewById<LinearLayout>(R.id.layoutIngredients) }
    private val layoutInstructions by lazy { view?.findViewById<LinearLayout>(R.id.layoutInstructions) }
    private val viewModel by lazy { ViewModelProvider(this)[EditRecipeViewModel::class.java] }
    private var recipeID: Long = -1 // Set the default value to -1 or retrieve it from arguments

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the recipe ID from the arguments
        recipeID = arguments?.getLong("recipeID", -1) ?: -1

        // TODO: Load existing recipe data using recipeID and populate the UI
        recipeID?.let { id ->
            viewModel.fetchRecipeById(id)
            viewModel.recipe.observe(viewLifecycleOwner) { existingRecipe ->
                existingRecipe?.let {
                    populateUI(view, it)
                }
            }
        }

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

        viewModel.recipeSaved.observe(viewLifecycleOwner) { saved ->
            if (saved != null) {
                showToast("Recipe updated successfully!")
                viewModel.recipeSaved.observe(viewLifecycleOwner) { recipeID ->
                    if (recipeID != null) {
                        Log.d("NewRecipesFragment", "Recipe: $recipeID")
                        findNavController().navigate(R.id.action_editRecipeFragment_to_recipeDetailFragment, bundleOf("recipeID" to recipeID))
                    } else {
                        Log.e("NewRecipesFragment", "Recipe is null.")
                    }
                }
            } else {
                showToast("Error updating recipe. Please try again.")
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
            recipeID = recipeID,
            name = recipeName.orEmpty(),
            description = recipeDescription.orEmpty(),
            ingredients = ingredientsList,
            instructions = instructionsList,
            imageUrl = recipeImageUrl
        )

        // Save the recipe to the database using ViewModel
        viewModel.saveRecipe(recipeModel)

        Log.d("EditRecipeFragment", "Recipe saved: $recipeModel")
    }

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

    private fun populateUI(view: View, recipe: RecipeModel) {
        view.findViewById<TextInputEditText>(R.id.editTextRecipeName).setText(recipe.name)
        view.findViewById<TextInputEditText>(R.id.editTextRecipeDescription).setText(recipe.description)
        view.findViewById<TextInputEditText>(R.id.editTextRecipeImageUrl).setText(recipe.imageUrl)

        recipe.ingredients.forEach {
            val textInputLayout = TextInputLayout(requireContext())
            val editText = TextInputEditText(requireContext())
            editText.hint = "Enter ingredient"
            editText.setTextColor(Color.WHITE)
            editText.setText(it.name)
            textInputLayout.addView(editText)

            layoutIngredients?.addView(textInputLayout)
        }

        recipe.instructions.forEach {
            val textInputLayout = TextInputLayout(requireContext())
            val editText = TextInputEditText(requireContext())
            editText.hint = "Enter instruction"
            editText.setTextColor(Color.WHITE)
            editText.setText(it)
            textInputLayout.addView(editText)

            layoutInstructions?.addView(textInputLayout)
        }
    }
}
