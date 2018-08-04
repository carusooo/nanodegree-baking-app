package com.example.macarus0.bakingapp.Model;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecipeJson {

    static public BaseRecipe[] fetchRecipes() {
        String recipeJsonString;
        try {
            String BAKING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
            recipeJsonString = fetchRecipeJson(BAKING_URL);
        } catch (IOException e) {
            Log.e("fetchRecipes", "Error fetching URL "+ e.getMessage());
            return null;
        }
        Gson gson = new Gson();
        BaseRecipe[] recipeLists = gson.fromJson(recipeJsonString, BaseRecipe[].class);
        Log.i("fetchRecipes", String.format("Retrieved %s recipes", recipeLists.length));
        return recipeLists;
    }

    private static String fetchRecipeJson(String urlString) throws IOException {
        String response;
        URL url = new URL(urlString);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        response = streamToString(httpURLConnection.getInputStream());
        httpURLConnection.disconnect();
        return response;
    }

    private static String streamToString(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder(inputStream.available());
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line).append('\n');
        }
        return stringBuilder.toString();
    }

}
