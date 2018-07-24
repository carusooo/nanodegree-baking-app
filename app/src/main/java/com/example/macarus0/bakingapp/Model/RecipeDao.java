package com.example.macarus0.bakingapp.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void insertAll(Recipe... recipes);


    @Query("select * from recipe")
    LiveData<List<Recipe>> getAllRecipes();

}
