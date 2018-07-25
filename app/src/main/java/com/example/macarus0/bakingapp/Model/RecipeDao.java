package com.example.macarus0.bakingapp.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("select * from baserecipe where id=:recipeId")
    LiveData<Recipe> getRecipeById(int recipeId);

    @Query("select * from baserecipe")
    LiveData<List<Recipe>> getAllRecipes();

}
