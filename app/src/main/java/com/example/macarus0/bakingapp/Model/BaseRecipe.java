package com.example.macarus0.bakingapp.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class BaseRecipe {
    @PrimaryKey
    int id;
    String name;
    @Ignore
    Ingredient[] ingredients;
    @Ignore
    Step[] steps;
    int servings;
    String image;

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Step[] getSteps() {
        return steps;
    }
    @Ignore
    public int getServings() {
        return servings;
    }
    @Ignore
    public String getImage() {
        return image;
    }
    @Ignore
    public int getId() {
        return id;
    }
    @Ignore
    public String getName() {
        return name;
    }


}


