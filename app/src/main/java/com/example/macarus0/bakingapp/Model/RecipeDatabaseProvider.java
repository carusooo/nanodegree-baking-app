package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Room;
import android.content.Context;

public class RecipeDatabaseProvider {

    private static RecipeDatabase mDb;

    public static RecipeDatabase getDatabase(Context context) {

        if(mDb == null){
            mDb = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), RecipeDatabase.class)
                    .build();
            Thread t = new Thread(() -> {
                Recipe[] recipeList = RecipeJson.fetchRecipes();
                mDb.getRecipeDao().insertAll(recipeList);
            });
            t.start();
        }

        return mDb;
    }
}
