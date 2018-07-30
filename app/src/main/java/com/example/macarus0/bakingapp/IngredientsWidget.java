package com.example.macarus0.bakingapp;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.macarus0.bakingapp.View.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int recipeId, String recipeName, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = getIngredientsRemoteViews(context, recipeId, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.ingredients_widget_list);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, 2, "Brownies", appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
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

    private static RemoteViews getIngredientsRemoteViews(Context context, int recipeId, String recipeName) {
        Log.e("IngrdtsRmtVs", "RecipeID: "+recipeId);
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra(RecipeActivity.RECIPE_ID, recipeId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        views.setTextViewText(R.id.ingredients_widget_recipe_name, recipeName);
        views.setPendingIntentTemplate(R.id.ingredients_widget_list, pendingIntent);
        Intent ingredientsAdapter = new Intent(context, IngredientsWidgetService.class);
        ingredientsAdapter.putExtra(IngredientsWidgetService.RECIPE_ID, recipeId);
        views.setRemoteAdapter(R.id.ingredients_widget_list, ingredientsAdapter);

        return views;
    }
}

