package com.roninn_creations.theproject.activities.registerActivity;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.roninn_creations.theproject.R;
import com.roninn_creations.theproject.activities.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterUITest {

    private static final String TEST_EMAIL = "test@mail.com";
    private static final String TEST_NAME = "Test User";
    private static final String TEST_PASSWORD = "Passw0rd";

    @Rule
    public ActivityTestRule<RegisterActivity> activityTestRule =
            new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void registerActivityRegisterTest() {

        onView(withId(R.id.edit_email))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_name))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_password))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_password2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_register))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edit_email))
                .perform(typeText(TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.edit_name))
                .perform(typeText(TEST_NAME), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.edit_password2))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.button_register)).perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.list_places))
                .check(matches(isDisplayed()));
    }
}
