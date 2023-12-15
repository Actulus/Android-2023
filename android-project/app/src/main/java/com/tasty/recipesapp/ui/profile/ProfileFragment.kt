package com.tasty.recipesapp.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.ui.recipe.adapter.RecipesListAdapter
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeListViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private val TAG: String? = RecipeRepository::class.java.canonicalName

class ProfileFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addRecipeButton = view.findViewById<View>(R.id.addRecipeButton)
        addRecipeButton.setOnClickListener {
            navigateToNewRecipe()
        }
        listMyRecipes()
    }

    private fun navigateToNewRecipe() {
        try {
            findNavController().navigate(R.id.action_profileFragment_to_recipeFormFragment)
        } catch (e: Exception) {
            Log.d(TAG, "navigateToNewRecipe: $e")
        }
    }

    private fun listMyRecipes() {
        val viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]

        context?.let {
            viewModel.fetchRecipeData(it)
        }

        viewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
            val adapter = RecipesListAdapter(recipes, requireContext(), onItemClickListener = {
                navigateToRecipeDetail(it)
            })
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recipes_recycler_view)
            if (recyclerView != null) {
                recyclerView.adapter = adapter
            }
            if (recyclerView != null) {
                recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel){
        Log.d(TAG, "navigateToRecipeDetail: $recipe")
        findNavController().navigate(
            R.id.action_profileFragment_to_recipeDetailFragment,
            bundleOf("recipeID" to recipe.recipeID)
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
            }
        }
    }
}