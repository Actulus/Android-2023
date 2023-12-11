package com.tasty.recipesapp.ui.recipe.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tasty.recipesapp.R
import com.tasty.recipesapp.databinding.RecipeListItemBinding
import com.tasty.recipesapp.repository.recipe.model.RecipeModel


class RecipesListAdapter(
    private var recipesList: List<RecipeModel>,
    private val context: Context,
    private val onItemClickListener: (RecipeModel) -> Unit ) :
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
        init {
            binding.root.setOnClickListener {
                Log.d("RecipesListAdapter", "RecipeItemViewHolder: Clicked on item")
                Log.d("RecipesListAdapter", "RecipeItemViewHolder: adapterPosition: $adapterPosition")
                onItemClickListener(recipesList[adapterPosition])
            }
        }

        fun bind(recipe: RecipeModel) {
            binding.recipeName.text = recipe.name
            binding.recipeDescription.text = recipe.description
            recipe.imageUrl?.let {
                binding.recipeImage.load(it) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_background)
                }
            }
        }
    }
}