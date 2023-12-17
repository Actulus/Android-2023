package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.FragmentRecipesBinding
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.ui.recipe.adapter.RecipesListAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val TAG: String? = RecipeRepository::class.java.canonicalName

class RecipesFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]

        context?.let {
            viewModel.fetchRecipeData(it)
        }

        viewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
            val adapter = RecipesListAdapter(recipes, requireContext(), onItemClickListener = {
                    navigateToRecipeDetail(it)
            })
            val recyclerView = view.findViewById<RecyclerView>(R.id.recipes_recycler_view)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.RIGHT
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    // Not used
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val deletedRecipe = recipes[viewHolder.adapterPosition]
                    showDeleteConfirmationDialog(deletedRecipe, viewModel)
                }
            })

            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel) {
        Log.d(TAG, "navigateToRecipeDetail: $recipe")
        findNavController().navigate(
            R.id.action_recipesFragment_to_recipeDetailFragment,
            bundleOf("recipeID" to recipe.recipeID)
        )
    }

    private fun showDeleteConfirmationDialog(deletedRecipe: RecipeModel, viewModel: RecipeListViewModel) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete recipe")
            .setMessage("Are you sure you want to delete ${deletedRecipe.name}?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.deleteRecipe(deletedRecipe)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                // User cancelled the dialog
                dialog?.dismiss()
                // Refresh recipes list
                viewModel.fetchRecipeData(requireContext())
            }
            .show()
    }
}