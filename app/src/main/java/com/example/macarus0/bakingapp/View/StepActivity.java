package com.example.macarus0.bakingapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.macarus0.bakingapp.R;

public class StepActivity extends AppCompatActivity implements StepFragment.StepNavigationHandler {

    public static final String STEP_ID = "step_id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        int stepId = intent.getIntExtra(STEP_ID, 0);

        StepFragment mStepFragment = new StepFragment();
        mStepFragment.setStepId(stepId);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_container, mStepFragment)
                .commit();

    }

    @Override
    public void navigateToStep(int stepId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        StepFragment navigationStep = new StepFragment();
        navigationStep.setStepId(stepId);
        fragmentManager.beginTransaction().replace(R.id.step_container, navigationStep).commit();
    }
}
