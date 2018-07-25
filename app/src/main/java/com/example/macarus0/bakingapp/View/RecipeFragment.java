package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Ingredient;
import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    private static final String RECIPE_ID = "recipe_id";
    private int mRecipeId;



    private void restoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
        }
    }

    @BindView(R.id.recipe_steps)
    public RecyclerView mStepRecyclerView;
    @BindView(R.id.recipe_ingredients)
    public TextView mIngredientsTextView;
    private StepAdapter mStepAdapter;

    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());

        mStepRecyclerView.setLayoutManager(mLinearLayoutManager);
        mStepAdapter = new StepAdapter();
        try {
            mStepAdapter.setStepClickHandler((StepAdapter.StepClickHandler) getContext());
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString()
                    + " must implement StepClickHandler");
        }
        mStepRecyclerView.setAdapter(mStepAdapter);


        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Log.i("onCreateView", "Looking for recipe ID " + mRecipeId);
        recipeViewModel.getRecipeById(mRecipeId).observe(this, this::displayRecipe);
        return rootView;
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    private void displayRecipe(Recipe recipe) {

            StringBuilder ingredients = new StringBuilder();
            for (Ingredient ingredient :
                    recipe.getIngredients()
                    ) {
                ingredients.append("• ").
                        append(ingredient.getQuantity()).append(" ").
                        append(ingredient.getMeasure()).append(" ").
                        append(ingredient.getIngredient()).append("\n");
            }
            mIngredientsTextView.setText(ingredients.toString());

        mStepAdapter.setSteps(recipe.getSteps());
    }

}
