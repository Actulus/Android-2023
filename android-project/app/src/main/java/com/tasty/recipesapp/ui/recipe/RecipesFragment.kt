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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.FragmentRecipesBinding
import com.tasty.recipesapp.repository.recipe.model.RecipeRepository
import com.tasty.recipesapp.repository.recipe.model.RecipeModel
import com.tasty.recipesapp.ui.recipe.viewmodel.RecipeListViewModel
import com.tasty.recipesapp.ui.recipe.adapter.RecipesListAdapter

class RecipesFragment : Fragment() {
    companion object {
        private val TAG: String? = RecipeRepository::class.java.canonicalName
//        const val BUNDLE_EXTRA_SELECTED_RECIPE_ID = "BUNDLE_EXTRA_SELECTED_RECIPE_ID"
    }

    private lateinit var binding: FragmentRecipesBinding
    private lateinit var recipesAdapter: RecipesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            val recipeID = it.getInt(BUNDLE_EXTRA_SELECTED_RECIPE_ID)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        initRecyclerView()

//        return inflater.inflate(R.layout.fragment_recipes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[RecipeListViewModel::class.java]

        context?.let {
            viewModel.fetchRecipeData(it)
        }

        viewModel.recipeList.observe(viewLifecycleOwner) { recipes ->
            val adapter = RecipesListAdapter(recipes, requireContext(), onItemClickListener = {
                    navigateToRecipeDetail(it)
            })
            val recyclerView = view.findViewById<RecyclerView>(R.id.recipes_recycler_view)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initRecyclerView() {
        recipesAdapter = RecipesListAdapter(listOf(), requireContext(), onItemClickListener = {
                recipe -> navigateToRecipeDetail(recipe)
        })

        binding.recipesRecyclerView.adapter = recipesAdapter
        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.recipesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun navigateToRecipeDetail(recipe: RecipeModel){
        Log.d(TAG, "navigateToRecipeDetail: $recipe")
        findNavController().navigate(
            R.id.action_recipesFragment_to_recipeDetailFragment,
            bundleOf("recipeID" to recipe.recipeID)
        )
    }
}