package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RecipeDatabaseProvider {

    private static RecipeDatabase mDb;

    public static RecipeDatabase getDatabase(Context context) {

        if (mDb == null) {
            mDb = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RecipeDatabase.class)
                    .build();
            Thread t = new Thread(() -> {
                BaseRecipe[] recipes = RecipeJson.fetchRecipes();
                mDb.getBaseRecipeDao().insertAll(recipes);
                for (BaseRecipe recipe :
                        recipes) {
                    for (Ingredient ingredient :
                            recipe.ingredients) {
                        ingredient.recipeId = recipe.getId();
                    }
                    for (Step step : recipe.steps) {
                        step.recipeId = recipe.getId();
                    }
                    mDb.getIngredientDao().insertAll(recipe.getIngredients());
                    mDb.getStepDao().insertAll(recipe.getSteps());
                }
            });
            t.start();
        }
        return mDb;
    }
}
