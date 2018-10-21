package com.aboutblank.baking_app;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

@RunWith(AndroidJUnit4.class)
public class UiNavigationTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testNavigation() {
        launchRecipeActivity();
        launchesDetailActivity();
        detailActivityNavigationNext();
        detailActivityNavigationPrev();
        detailPressBackToMain();
    }

    public void launchRecipeActivity() {
        onView(withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_toolbar)).check(matches(isDisplayed()));
    }

    public void launchesDetailActivity() {
        onView(withId(R.id.recipe_recycler))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredient_recycler)).check(matches(isDisplayed()));
    }

    public void detailActivityNavigationNext() {
        onView(withId(R.id.ingredient_recycler)).check(matches(isDisplayed()));

        onView(withId(R.id.detail_next))
                .perform(click());
    }

    public void detailActivityNavigationPrev() {
        onView(withId(R.id.detail_media_card)).check(matches(isDisplayed()));

        onView(withId(R.id.detail_previous))
                .perform(click());

        onView(withId(R.id.ingredient_recycler)).check(matches(isDisplayed()));
    }

    public void detailPressBackToMain() {
        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.recipe_toolbar)).check(matches(isDisplayed()));

        onView(isRoot()).perform(pressBack());

        onView(withId(R.id.main_toolbar)).check(matches(isDisplayed()));
    }

    // Get current active activity
    // http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html
    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Activity currentActivity;
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = (Activity) resumedActivities.iterator().next();
                activity[0] = currentActivity;
            }
        });

        return activity[0];
    }
}
