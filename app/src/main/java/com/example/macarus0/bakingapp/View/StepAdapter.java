package com.example.macarus0.bakingapp.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.macarus0.bakingapp.Model.Step;
import com.example.macarus0.bakingapp.R;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private final String TAG = "StepAdapter";

    private List<Step> mSteps;

    public void setStepClickHandler(StepClickHandler stepClickHandler) {
        this.stepClickHandler = stepClickHandler;
    }

    private StepClickHandler stepClickHandler;

    public void setSteps(List<Step> mSteps){
        this.mSteps = mSteps;
        Log.i(TAG, "setSteps: " + mSteps.size());
        notifyDataSetChanged();
    }

    public interface StepClickHandler {
        void onStepClick(int id);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.card_step, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step step = this.mSteps.get(position);
        holder.stepNameTextView.setText(step.getShortDescription());
        holder.stepId = step.getStepId();
        Log.i(TAG, "onBindViewHolder: "+ step.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mSteps == null) {
            Log.i(TAG, "No steps yet: ");
            return  0;
        }
        return mSteps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView stepNameTextView;
        int stepId;

        ViewHolder(View v) {
            super(v);
            stepNameTextView = v.findViewById(R.id.step_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            stepClickHandler.onStepClick(stepId);
        }
    }
}
