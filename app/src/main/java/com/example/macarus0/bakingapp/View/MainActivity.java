package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.Util.BakingIdlingResource;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickHandler{

    private RecipeAdapter mRecipeAdapter;
    private BakingIdlingResource mBakingIdlingResource;
    private RecipeViewModel mRecipeViewModel;
    @BindView(R.id.offline_icon_imageview)
    public ImageView mOfflineIcon;
    @BindView(R.id.offline_error_text)
    public TextView mOfflineErrorTextView;
    @BindView(R.id.offline_error_retry_button)
    public Button mOfflineRetryButton;
    @BindView(R.id.offline_error_main)
    ConstraintLayout mOfflineError;
    @BindView(R.id.recipe_recycler_view)
    public RecyclerView mRecipeRecyclerView;

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
        ButterKnife.bind(this);
        mBakingIdlingResource = BakingIdlingResource.getIdlingResource();
        mBakingIdlingResource.startWork();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.recipeGridColumns));
        mRecipeAdapter = new RecipeAdapter();
        mRecipeAdapter.setRecipeClickHandler(this);
        mRecipeRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecipeRecyclerView.setAdapter(mRecipeAdapter);
        loadRecipes();
    }

    private void loadRecipes() {
        mBakingIdlingResource.startWork();
        RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        recipeViewModel.getAllRecipes().observe(this, recipes -> showRecipes(recipes));
    }


    private void showRecipes(List<Recipe> recipes){
        Log.i("showRecipes", "Showing " + recipes.size() + " recipes");
        if(recipes.size() == 0) {
            mOfflineIcon.setImageResource(R.drawable.ic_cloud_off_grey_24dp);
            mOfflineErrorTextView.setText(R.string.offline_error_message);
            mOfflineRetryButton.setText(R.string.offline_retry_text);
            mOfflineRetryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadRecipes();
                }
            });
            mOfflineError.setVisibility(View.VISIBLE);
        } else {
            mOfflineError.setVisibility(View.INVISIBLE);
            mRecipeAdapter.setRecipes(recipes);
        }
        mBakingIdlingResource.completeWork();
    }


}
