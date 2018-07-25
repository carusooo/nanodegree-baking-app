package com.example.macarus0.bakingapp.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macarus0.bakingapp.R;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickHandler{

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

    }


}
