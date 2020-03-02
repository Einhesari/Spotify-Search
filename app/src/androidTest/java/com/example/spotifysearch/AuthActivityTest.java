package com.example.spotifysearch;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class AuthActivityTest {

    @Test
    public void testDialogShow() {
        onView(ViewMatchers.withId(R.id.btn_authorization)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_authorization)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.dialog_root)).inRoot(isDialog()).check(matches(isDisplayed()));
    }

    @Test
    public void testDialogCancel() {
        onView(ViewMatchers.withId(R.id.btn_authorization)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.img_webview_close)).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.btn_authorization)).check(matches(isDisplayed()));
    }


}
