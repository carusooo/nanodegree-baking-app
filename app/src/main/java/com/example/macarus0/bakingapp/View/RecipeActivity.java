package com.example.macarus0.bakingapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.macarus0.bakingapp.R;

public class RecipeActivity extends AppCompatActivity implements StepAdapter.StepClickHandler{

    public static final String RECIPE_ID = "recipe_id";

    @Override
    public void onStepClick(int id) {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra(StepActivity.STEP_ID, id);
        startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        int recipeId = intent.getIntExtra(RECIPE_ID, 0);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeId(recipeId);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();

    }
}
