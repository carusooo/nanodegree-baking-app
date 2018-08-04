package com.example.macarus0.bakingapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.constraint.ConstraintLayout;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.Model.RecipeDatabase;
import com.example.macarus0.bakingapp.Model.RecipeDatabaseProvider;
import com.example.macarus0.bakingapp.Model.Step;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private final Context applicationContext;
    private RecipeDatabase mDb;

    public RecipeViewModel(Application application) {
        super(application);
        applicationContext = application.getApplicationContext();
    }

    private RecipeDatabase getmDb() {
        if(mDb == null){
            mDb = RecipeDatabaseProvider.getDatabase(applicationContext);
        }
        return mDb;
    }

    public LiveData<List<Recipe>> getAllRecipes() {

        LiveData<List<Recipe>> recipes = getmDb().getRecipeDao().getAllRecipes();
        if( recipes.getValue() == null) {
            mDb = null;
        }
        return recipes;
    }

    public LiveData<Recipe> getRecipeById(int id) {
        return getmDb().getRecipeDao().getRecipeById(id);
    }

    public LiveData<Step> getStepById(int id) {
        return getmDb().getStepDao().getStepById(id);
    }


    public LiveData<Step> getNextStep(Step step) {
        return getmDb().getStepDao().getStepByPosition(step.getRecipeId(), step.getStepNumber() + 1);
    }

    public LiveData<Step> getPreviousStep(Step step) {
        return getmDb().getStepDao().getStepByPosition(step.getRecipeId(), step.getStepNumber() - 1);
    }

    public LiveData<Step> getFirstStep(int recipeId) {
        return getmDb().getStepDao().getStepByPosition(recipeId, 1);
    }
}

