package com.example.macarus0.bakingapp.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface RecipeWithIngredientsAndStepsDao {

    @Query("select * from recipe where id=:recipeId")
    LiveData<RecipeWithIngredientsAndSteps> getRecipeById(int recipeId);
}
