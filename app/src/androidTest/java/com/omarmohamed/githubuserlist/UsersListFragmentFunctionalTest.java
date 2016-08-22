package com.omarmohamed.githubuserlist;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.omarmohamed.githubuserlist.activities.MainActivity;
import com.omarmohamed.githubuserlist.utils.Utilities;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test for UsersListFragment component
 */
@RunWith(AndroidJUnit4.class)
public class UsersListFragmentFunctionalTest {

    /**
     * Provide functional testing of a single activity
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.omarmohamed.githubuserlist", appContext.getPackageName());
    }

    /**
     * Check if an user card is displayed correctly
     *
     * @throws Exception
     */
    @Test
    public void listOrSnackbarAreVisible() throws Exception {
        if (Utilities.hasActiveInternetConnection(InstrumentationRegistry.getTargetContext())) {
            onView(withId(R.id.list)).check(matches(isDisplayed()));
        } else {
            onView(withId(android.support.design.R.id.snackbar_text))
                    .check(matches(isDisplayed()));
        }
    }

}
