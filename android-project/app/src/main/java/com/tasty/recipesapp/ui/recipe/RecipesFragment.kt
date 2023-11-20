package com.tasty.recipesapp.ui.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.R
import com.tasty.recipesapp.repository.recipe.RecipeRepository
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
            /*for (recipe in recipes) {
                Log.d(TAG, "Recipe: $recipe")
            }*/

            val adapter = RecipesListAdapter(recipes, requireContext())
            val recyclerView = view.findViewById<RecyclerView>(R.id.recipes_recycler_view)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecipesFragment().apply {
                arguments = Bundle().apply {
                     putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2, param2)
                }
            }
    }
}