package com.tasty.recipesapp.ui.recipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tasty.recipesapp.databinding.RecipeListItemBinding
import com.tasty.recipesapp.repository.recipe.model.RecipeModel


class RecipesListAdapter(private val recipesList: List<RecipeModel>, private val context: Context) :
    RecyclerView.Adapter<RecipesListAdapter.RecipeItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipesListAdapter.RecipeItemViewHolder {
        val binding = RecipeListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RecipeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val currentRecipe: RecipeModel = recipesList[position]

        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    inner class RecipeItemViewHolder(private val binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel) {
            binding.recipeName.text = recipe.name
            binding.recipeDescription.text = recipe.description
            binding.recipeImageUrl.text = recipe.imageUrl
        }
    }


}