package com.example.macarus0.bakingapp.View;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.macarus0.bakingapp.IngredientsWidget;
import com.example.macarus0.bakingapp.IngredientsWidgetService;
import com.example.macarus0.bakingapp.Model.Recipe;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

public class RecipeActivity extends AppCompatActivity implements StepAdapter.StepClickHandler,
        StepFragment.StepNavigationHandler, RecipeFragment.OnFragmentSetupListener {

    public static final String RECIPE_ID = "recipe_id";

    FragmentManager mFragmentManager;
    RecipeViewModel mRecipeViewModel;
    int mRecipeId;
    Recipe mRecipe;

    @Override
    public void navigateToStep(int stepId) {
        setStepFragment(stepId);
    }

    @Override
    public void onStepClick(int stepId) {
        if (findViewById(R.id.step_container) != null) {
            setStepFragment(stepId);
        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.STEP_ID, stepId);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        mRecipeId = intent.getIntExtra(RECIPE_ID, 0);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setRecipeId(mRecipeId);
        mFragmentManager = getSupportFragmentManager();

        mFragmentManager.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();

        if (findViewById(R.id.step_container) != null) {
            mRecipeViewModel.getFirstStep(mRecipeId).observe(this, step -> setStepFragment(step.getStepId()));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);
        return true;
    }

    private void setStepFragment(int stepId) {
        StepFragment  stepFragment = new StepFragment();
        stepFragment.setStepId(stepId);
        mFragmentManager.beginTransaction().replace(R.id.step_container, stepFragment).commit();
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_widget:
                mRecipeViewModel.getRecipeById(mRecipeId).observe(this, recipe -> updateIngredientsWidget(recipe));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }    }

        private void updateIngredientsWidget(Recipe recipe) {

            Intent updateWidgetIntent = new Intent(this, IngredientsWidget.class);
            updateWidgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            updateWidgetIntent.putExtra(IngredientsWidgetService.RECIPE_NAME, recipe.getName());
            updateWidgetIntent.putExtra(IngredientsWidgetService.RECIPE_ID, recipe.getId());

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
            if(appWidgetIds != null && appWidgetIds.length > 0) {
                updateWidgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                this.sendBroadcast(updateWidgetIntent);
            }


        }
}
