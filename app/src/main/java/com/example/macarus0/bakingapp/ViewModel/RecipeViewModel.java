package com.example.macarus0.bakingapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.Model.RecipeDatabase;
import com.example.macarus0.bakingapp.Model.RecipeDatabaseProvider;
import com.example.macarus0.bakingapp.Model.Step;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private final RecipeDatabase mDb;

    public RecipeViewModel(Application application){
        super(application);
        mDb = RecipeDatabaseProvider.getDatabase(application.getApplicationContext());
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return mDb.getRecipeDao().getAllRecipes();
    }

    public LiveData<Recipe> getRecipeById(int id) {
        return mDb.getRecipeDao().getRecipeById(id);
    }

    public LiveData<Step> getStepById(int id) { return mDb.getStepDao().getStepById(id);}


}

