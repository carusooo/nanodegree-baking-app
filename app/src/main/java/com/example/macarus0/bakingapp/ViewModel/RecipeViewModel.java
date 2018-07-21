package com.example.macarus0.bakingapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.Model.RecipeJson;

import java.util.Arrays;
import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();

    public RecipeViewModel(Application application){
        super(application);
        loadRecipes();
    }

    public LiveData<List<Recipe>> getRecipeList() {
        return recipeLiveData;
    }

    private void loadRecipes(){
        new AsyncTask<Void, Void, Recipe[]>() {
            @Override
            protected Recipe[] doInBackground(Void... voids) {
                Recipe[] recipeList = RecipeJson.fetchRecipes();
                return recipeList;
            }

            @Override
            protected void onPostExecute(Recipe[] recipeList) {
                recipeLiveData.setValue(Arrays.asList(recipeList));
            }
        }.execute();


    }


}

