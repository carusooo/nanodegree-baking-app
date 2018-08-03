package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.Util.BakingIdlingResource;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickHandler{

    private RecipeAdapter mRecipeAdapter;
    private BakingIdlingResource mBakingIdlingResource;

    @Override
    public void onRecipeClick(int id) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.RECIPE_ID, id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipeViewModel mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recipe_recycler_view);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recipeGridColumns));
        mRecipeAdapter = new RecipeAdapter();
        mRecipeAdapter.setRecipeClickHandler(this);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mRecipeAdapter);

        mBakingIdlingResource = BakingIdlingResource.getIdlingResource();
        mBakingIdlingResource.startWork();
        mRecipeViewModel.getAllRecipes().observe(this, recipes -> loadRecipes(recipes));
        mBakingIdlingResource.completeWork();

    }

    private void loadRecipes(List<Recipe> recipes){
        mRecipeAdapter.setRecipes(recipes);
        mBakingIdlingResource.completeWork();
    }


}
