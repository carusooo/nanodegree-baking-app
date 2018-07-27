package com.example.macarus0.bakingapp.View;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Step;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @BindView(R.id.step_text)
    TextView mStepTextTextView;
    @BindView(R.id.step_video)
    TextView mStepVideoTextView;
    @BindView(R.id.next_step)
    Button mNextStep;
    @BindView(R.id.previous_step)
    Button mPreviousStep;
    private int mStepId;
    private RecipeViewModel recipeViewModel;

    public void setStepId(int mStepId) {
        this.mStepId = mStepId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        ButterKnife.bind(this, rootView);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Log.i("onCreateView", "Looking for step ID " + mStepId);
        recipeViewModel.getStepById(mStepId).observe(this, this::displayStep);


        return rootView;
    }

    private void restoreState(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String STEP_ID = "step_id";
            mStepId = savedInstanceState.getInt(STEP_ID);
        }
    }

    private void displayStep(Step step) {
        mStepTextTextView.setText(step.getDescription());
        Log.i("displayStep", "Setting step description: " + step.getDescription());
        mStepVideoTextView.setText(step.getVideoUrl());
        recipeViewModel.getNextStep(step).observe(this, nextStep -> setStepNavigationCallback(nextStep, mNextStep));
        recipeViewModel.getPreviousStep(step).observe(this, previousStep -> setStepNavigationCallback(previousStep, mPreviousStep));

    }

    private void setStepNavigationCallback(Step step, Button button) {
        if (step == null) {
            button.setEnabled(false);
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepNavigationHandler activity = (StepNavigationHandler) getActivity();
                    activity.navigateToStep(step.getStepId());
                }
            });
        }

    }

    public interface StepNavigationHandler {
        void navigateToStep(int id);
    }


}
