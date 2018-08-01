package com.example.macarus0.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.macarus0.bakingapp.View.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {
    private int recipeId;
    private String recipeName;

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        if(recipeName != null) {
            // Construct the RemoteViews object
            RemoteViews views = getIngredientsRemoteViews(context, recipeId, recipeName);
            // Instruct the widget manager to update the widget
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredients_widget_list);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        } else {
            Log.e("updateAppWidget", "No recipe specified!");
        }
    }

    private RemoteViews getIngredientsRemoteViews(Context context, int recipeId, String recipeName) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.ingredients_widget_recipe_name, recipeName);
        Intent ingredientsAdapter = new Intent(context, IngredientsWidgetService.class);
        // Set data here so the intent isn't filtered
        ingredientsAdapter.setData(Uri.fromParts("content", String.valueOf(recipeId), null));
        ingredientsAdapter.putExtra(IngredientsWidgetService.RECIPE_ID, recipeId);
        views.setRemoteAdapter(R.id.ingredients_widget_list, ingredientsAdapter);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        recipeId = intent.getIntExtra(IngredientsWidgetService.RECIPE_ID, 0);
        recipeName = intent.getStringExtra(IngredientsWidgetService.RECIPE_NAME);
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        Log.e("onAppWidgetOptions", "Called!");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }
}

