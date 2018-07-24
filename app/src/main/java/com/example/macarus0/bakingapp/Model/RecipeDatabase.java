package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {BaseRecipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract BaseRecipeDao getBaseRecipeDao();
    public abstract IngredientDao getIngredientDao();
    public abstract StepDao getStepDao();
    public abstract RecipeDao getRecipeDao();
}
