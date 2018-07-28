package com.example.macarus0.bakingapp.View;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {



    @BindView(R.id.player_view)
    PlayerView playerView;
    @BindView(R.id.step_text)
    TextView mStepTextTextView;
    @BindView(R.id.next_step)
    Button mNextStep;
    @BindView(R.id.previous_step)
    Button mPreviousStep;
    private int mStepId;
    private RecipeViewModel recipeViewModel;
    private SimpleExoPlayer mPlayer;
    private long mPlaybackPosition;
    private int mCurrentWindow;
    private boolean playWhenReady = true;

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

    private void insertVideo(Step step) {
        MediaSource mediaSource = buildMediaSource(Uri.parse(step.getVideoUrl()));
        mPlayer.prepare(mediaSource, true, false);
    }

    private void initializePlayer() {
        if(mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(mPlayer);

            mPlayer.setPlayWhenReady(playWhenReady);
            mPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        String userAgent = Util.getUserAgent(getContext(), "BakingApp");
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                .createMediaSource(uri);
    }

    private void displayStep(Step step) {
        mStepTextTextView.setText(step.getDescription());
        Log.i("displayStep", "Setting step description: " + step.getDescription());
        recipeViewModel.getNextStep(step).observe(this, nextStep -> setStepNavigationCallback(nextStep, mNextStep));
        recipeViewModel.getPreviousStep(step).observe(this, previousStep -> setStepNavigationCallback(previousStep, mPreviousStep));
        if(step.getVideoUrl() != null) {
            insertVideo(step);
        }

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

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mCurrentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
