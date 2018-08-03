package com.example.macarus0.bakingapp.Util;

import android.support.test.espresso.IdlingResource;
import android.util.Log;

public class BakingIdlingResource implements IdlingResource {

    static BakingIdlingResource sIdlingResource;

    public static BakingIdlingResource getIdlingResource() {
        if(sIdlingResource == null){
            sIdlingResource = new BakingIdlingResource();
        }
        return sIdlingResource;
    }


    ResourceCallback resourceCallback;
    private boolean mIsIdleNow;
    static String sName = "BakingIdlingResource";

    @Override
    public String getName() {
        return sName;
    }

    @Override
    public boolean isIdleNow() {
        Log.e("IdlingResource", "Is idle? "+ mIsIdleNow);
        return mIsIdleNow;
    }

    public void startWork() {
        mIsIdleNow = false;
    }

    public void completeWork() {
        mIsIdleNow = true;
        if(resourceCallback != null){

        }
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
