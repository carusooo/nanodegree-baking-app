package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

@Dao
public class Recipe {

    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Ingredient> ingredients;
    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Step> steps;
    @Embedded
    BaseRecipe baseRecipe;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getName() {
        return baseRecipe.getName();
    }

    public int getId() {
        return baseRecipe.getId();
    }
}
