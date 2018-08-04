package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import com.example.macarus0.bakingapp.Util.BakingIdlingResource;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {

    private static final String RECIPE_ID = "recipe_id";
    @BindView(R.id.recipe_steps)
    public RecyclerView mStepRecyclerView;
    @BindView(R.id.recipe_ingredients)
    public TextView mIngredientsTextView;
    private BakingIdlingResource mBakingIdlingResource;
    private int mRecipeId;
    private StepAdapter mStepAdapter;
    private OnFragmentSetupListener onFragmentSetupListener;
    public RecipeFragment() {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RECIPE_ID, mRecipeId);
    }

    private void restoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        ButterKnife.bind(this, rootView);

        /*
         *  Create the layout manager for the reviews and attach to the recyclerView
         *  Scrolling is disabled here since the content is wrapped by a ScrollView
         */
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mStepRecyclerView.setLayoutManager(mLinearLayoutManager);
        mStepAdapter = new StepAdapter();
        try {
            mStepAdapter.setStepClickHandler((StepAdapter.StepClickHandler) getContext());
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString()
                    + " must implement StepClickHandler");
        }
        mStepRecyclerView.setHasFixedSize(true);
        mStepRecyclerView.setAdapter(mStepAdapter);


        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Log.i("onCreateView", "Looking for recipe ID " + mRecipeId);
        mBakingIdlingResource = BakingIdlingResource.getIdlingResource();

        mBakingIdlingResource.startWork();
        recipeViewModel.getRecipeById(mRecipeId).observe(this, this::displayRecipe);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onFragmentSetupListener = ((OnFragmentSetupListener) getActivity());
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentSetupListener");
        }
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    private void displayRecipe(Recipe recipe) {
        mStepAdapter.setSteps(recipe.getSteps());
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
        onFragmentSetupListener.setTitle(recipe.getName());
        mBakingIdlingResource.completeWork();
    }

    public interface OnFragmentSetupListener {
        void setTitle(String title);
    }
}
