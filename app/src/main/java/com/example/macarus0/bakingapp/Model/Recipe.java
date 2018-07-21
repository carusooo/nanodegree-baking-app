package com.example.macarus0.bakingapp.Model;


public class Recipe {
    int id;
    String name;
    Ingredient[] ingredients;
    Step[] steps;
    int servings;
    String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public class Ingredient {
        float quantity;
        String measure;
        String ingredient;
    }

    public class Step {
        int id;
        String shortDescription;
        String description;
        String videoUrl;
        String thumbnailUrl;

    }
}


