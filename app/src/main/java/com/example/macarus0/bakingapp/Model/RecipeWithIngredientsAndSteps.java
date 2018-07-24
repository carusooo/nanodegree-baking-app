package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

@Dao
public class RecipeWithIngredientsAndSteps {

    @Embedded
    Recipe recipe;

    @Relation(parentColumn = "id",
            entityColumn = "recipeId")public List<Ingredient >ingredients;

    @Relation(parentColumn = "id",
    entityColumn = "recipeId")public List<Step> steps;


}
