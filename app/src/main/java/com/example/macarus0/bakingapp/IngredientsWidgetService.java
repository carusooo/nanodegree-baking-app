package com.example.macarus0.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.Model.RecipeDatabase;
import com.example.macarus0.bakingapp.Model.RecipeDatabaseProvider;

public class IngredientsWidgetService extends RemoteViewsService {

    public static final String RECIPE_ID = "com.example.macarus0.bakingapp.widget.recipe_id";
    public static final String RECIPE_NAME = "com.example.macarus0.bakingapp.widget.recipe_name";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("RemoteViewsFactory", "onGetViewFactory");
        return new IngredientsRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private final int mRecipeId;
    private RecipeDatabase mDb;
    private Recipe mRecipe;


    public IngredientsRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mRecipeId = intent.getIntExtra(IngredientsWidgetService.RECIPE_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        Log.e("IngredientsViewsFactory", "Recipe ID "+ mRecipeId);

    }

    @Override
    public void onCreate() {
        mDb = RecipeDatabaseProvider.getDatabase(mContext.getApplicationContext());
    }

    @Override
    public void onDataSetChanged() {
        // Get the recipe Dao here
        mRecipe = mDb.getRecipeDao().getStaticRecipeById(mRecipeId);
        Log.e("OnDataSetChanged", "Got Recipe "+mRecipe.getName());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mRecipe == null) return 0;
        return mRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredients_item_widget);
        rv.setTextViewText(R.id.ingredient_item_text, mRecipe.getIngredients().get(position).getIngredient());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
