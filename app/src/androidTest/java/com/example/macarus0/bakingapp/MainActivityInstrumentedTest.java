package com.example.macarus0.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.macarus0.bakingapp.Util.BakingIdlingResource;
import com.example.macarus0.bakingapp.View.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    IdlingRegistry idlingRegistry;
    BakingIdlingResource mBakingIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        idlingRegistry = IdlingRegistry.getInstance();
        mBakingIdlingResource = BakingIdlingResource.getIdlingResource();
        idlingRegistry.register(mBakingIdlingResource);
    }

    @Test
    public void recipesAreDisplayed() {
        // Verify that a clickable recipe is displayed
        // Add a sleep here for RecyclerView layout
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            Log.e("Test", "Interrupted!");
        }

        onView(ViewMatchers.withContentDescription("Brownies"))
                .perform(click());
        onView(withId(R.id.recipe_ingredients)).check(matches(withSubstring("chocolate")));
    }

    @After
    public void cleanUp(){
        idlingRegistry.unregister(mBakingIdlingResource);
    }
}
