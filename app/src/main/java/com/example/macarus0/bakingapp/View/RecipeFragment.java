package com.example.macarus0.bakingapp.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment {
    public static String RECIPE_ID = "recipe_index";
    protected int mRecipeId;


    protected void restoreState (@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRecipeId = savedInstanceState.getInt(RECIPE_ID);
        }
    }




    private RecipeViewModel recipeViewModel;
    @BindView(R.id.recipe_steps)
    public RecyclerView mStepRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //private StepAdapter mStepAdapter;

    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        ButterKnife.bind(this, rootView);

//        mLinearLayoutManager = new LinearLayoutManager(getContext());
//
//        mStepRecyclerView.setLayoutManager(mLinearLayoutManager);
//        mStepAdapter = new StepAdapter();
//        mStepRecyclerView.setAdapter(mStepAdapter);
//
//        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
//        Log.i("onCreateView", "Looking for recipe ID " + mRecipeId);
//        recipeViewModel.getRecipeById(mRecipeId).observe(this, recipe -> displayRecipe(recipe));
        return rootView;
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

//    private void displayRecipe(RecipeWithStepsAndIngredients recipe) {
//
//        private void displayIngredients(RecipeWithStepsAndIngredients recipe) {
//            StringBuilder ingredients = new StringBuilder();
//            for (Ingredient ingredient :
//                    recipe.getIngredients()
//                    ) {
//                ingredients.append("• ").
//                        append(ingredient.getQuantity()).append(" ").
//                        append(ingredient.getMeasure()).append(" ").
//                        append(ingredient.getIngredient()).append("\n");
//            }
//            mIngredientsTextView.setText(ingredients.toString());
//        }
//        mStepAdapter.setSteps(recipe.getSteps());
//    }

}
