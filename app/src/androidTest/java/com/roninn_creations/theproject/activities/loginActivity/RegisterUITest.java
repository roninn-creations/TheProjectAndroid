package com.roninn_creations.theproject.activities.loginActivity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.activities.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterUITest {

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void LoginActivityRegisterTest() {

        onView(withId(R.id.button_register))
                .check(matches(isDisplayed()));

        onView(withId(R.id.button_register)).perform(click());

        onView(withId(R.id.edit_email))
                .check(matches(isDisplayed()));
    }
}
