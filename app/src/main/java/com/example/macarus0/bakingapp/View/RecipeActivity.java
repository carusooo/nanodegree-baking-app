package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity implements StepAdapter.StepClickHandler,
        StepFragment.StepNavigationHandler {

    public static final String RECIPE_ID = "recipe_id";

    FragmentManager mFragmentManager;
    RecipeViewModel mRecipeViewModel;

    @Override
    public void navigateToStep(int stepId) {
        setStepFragment(stepId);
    }

    @Override
    public void onStepClick(int stepId) {
        if (findViewById(R.id.step_container) != null) {
            setStepFragment(stepId);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.STEP_ID, stepId);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        int recipeId = intent.getIntExtra(RECIPE_ID, 0);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeId(recipeId);
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();

        if (findViewById(R.id.step_container) != null) {
            mRecipeViewModel.getFirstStep(recipeId).observe(this, step -> setStepFragment(step.getStepId()));
        }

    }

    private void setStepFragment(int stepId) {
        StepFragment  stepFragment = new StepFragment();
        stepFragment.setStepId(stepId);
        mFragmentManager.beginTransaction().replace(R.id.step_container, stepFragment).commit();
    }


}
