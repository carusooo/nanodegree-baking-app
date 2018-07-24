package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDao getRecipeDao();
    public abstract RecipeWithIngredientsAndStepsDao getRecipeWithIngredientsAndStepsDao();
}
