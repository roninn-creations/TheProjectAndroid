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
public class LoginUITest {

    private static final String TEST_EMAIL = "shark@mail.com";
    private static final String TEST_PASSWORD = "Passw0rd";

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityLoginTest() {

        onView(withId(R.id.edit_email))
                .check(matches(isDisplayed()));
        onView(withId(R.id.edit_password))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_login))
                .check(matches(isDisplayed()));

        onView(withId(R.id.edit_email))
                .perform(typeText(TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.edit_password))
                .perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.button_login)).perform(click());

        onView(withId(R.id.progress_bar))
                .check(matches(isDisplayed()));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.list_places))
                .check(matches(isDisplayed()));
    }
}
