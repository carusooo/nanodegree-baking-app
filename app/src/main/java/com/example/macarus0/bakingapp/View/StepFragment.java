package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Step;
import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    public void setStepId(int mStepId) {
        this.mStepId = mStepId;
    }

    private int mStepId;

    @BindView(R.id.step_text)
    TextView mStepTextTextView;
    @BindView(R.id.step_video)
    TextView mStepVideoTextView;
    @BindView(R.id.next_step)
    TextView mNextStepTextView;


    private RecipeViewModel recipeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreState(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.step_fragment, container, false);
        ButterKnife.bind(this, rootView);

        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Log.i("onCreateView", "Looking for step ID " + mStepId);
        recipeViewModel.getStepById(mStepId).observe(this, this::displayStep);
        mNextStepTextView.setText("Next Step");
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
        Log.i("displayStep", "Setting step description: "+step.getDescription());
        mStepVideoTextView.setText(step.getVideoUrl());

    }



}
