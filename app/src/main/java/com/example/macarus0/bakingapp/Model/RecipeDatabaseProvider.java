package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.regex.Pattern;

public class RecipeDatabaseProvider {

    private static RecipeDatabase mDb;
    private static final String STEP_NUMBER_REGEX = "^\\d+\\.\\s+";

    public static RecipeDatabase getDatabase(Context context) {

        if (mDb == null) {
            mDb = createDatabase(context);
        }
        return mDb;
    }

    private static RecipeDatabase createDatabase(Context context) {
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
                int stepNumber = 1;
                for (Step step : recipe.steps) {
                    step.recipeId = recipe.getId();
                    step.setStepNumber(stepNumber++);
                    step.setDescription(cleanUpStepNumber(step.getDescription()));
                }
                mDb.getIngredientDao().insertAll(recipe.getIngredients());
                mDb.getStepDao().insertAll(recipe.getSteps());
            }
        });
        t.start();
        return mDb;
    }

    private static String cleanUpStepNumber(String stepText){
        String  cleanedUpString = stepText.replaceFirst(STEP_NUMBER_REGEX, "");
        return cleanedUpString;

    }
}
