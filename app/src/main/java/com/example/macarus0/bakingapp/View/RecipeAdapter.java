package com.example.macarus0.bakingapp.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final String TAG = "RecipeAdapter";

    private List<Recipe> recipes;

    public void setRecipeClickHandler(RecipeClickHandler recipeClickHandler) {
        this.recipeClickHandler = recipeClickHandler;
    }

    private RecipeClickHandler recipeClickHandler;

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        Log.i(TAG, "setRecipes: " + recipes.size());
        notifyDataSetChanged();
    }

    public interface RecipeClickHandler {
        void onRecipeClick(int id);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.card_recipe, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = this.recipes.get(position);
        holder.recipeNameTextView.setText(recipe.getName());
        holder.recipeId = recipe.getId();
        Log.i(TAG, "onBindViewHolder: "+ recipe.getName());
    }

    @Override
    public int getItemCount() {
        if(recipes == null) {
            Log.i(TAG, "No recipes yet: ");
            return  0;
        }
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView recipeImageView;
        final TextView recipeNameTextView;
        int recipeId;

        ViewHolder(View v) {
            super(v);
            recipeImageView = v.findViewById(R.id.recipe_image);
            recipeNameTextView = v.findViewById(R.id.recipe_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipeClickHandler.onRecipeClick(recipeId);
        }
    }
}
