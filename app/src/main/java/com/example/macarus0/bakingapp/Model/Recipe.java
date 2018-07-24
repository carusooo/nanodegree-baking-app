package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

@Dao
public class Recipe {

    @Embedded
    BaseRecipe baseRecipe;

    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Ingredient> ingredients;
    @Relation(parentColumn = "id",
            entityColumn = "recipeId")
    public List<Step> steps;


    public String getName() {
        return baseRecipe.getName();
    }

    public int getId() {
        return baseRecipe.getId();
    }
}
