package com.example.macarus0.bakingapp.Model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Recipe.class,
        parentColumns = "id", childColumns = "recipeId"))
public class Ingredient {
    @PrimaryKey
    int ingredientId;

    int recipeId;
    int id;
    float quantity;
    String measure;
    String ingredient;

    @Ignore
    public int getIngredientId() {
        return ingredientId;
    }

    @Ignore
    public int getRecipeId() {
        return recipeId;
    }

    @Ignore
    public int getId() {
        return id;
    }

    @Ignore
    public float getQuantity() {
        return quantity;
    }

    @Ignore
    public String getMeasure() {
        return measure;
    }

    @Ignore
    public String getIngredient() {
        return ingredient;
    }
}
