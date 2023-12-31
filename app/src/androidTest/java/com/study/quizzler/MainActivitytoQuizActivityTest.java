package com.study.quizzler;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivitytoQuizActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivitytoQuizActivityTest() {
        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        withParent(allOf(withId(androidx.navigation.ui.R.id.action_bar),
                                withParent(withId(androidx.navigation.ui.R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton.check(matches(isDisplayed()));

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.main_activity_recycler_view_button), withText("All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_activity_recycler_view),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction frameLayout = onView(
                allOf(withId(androidx.navigation.ui.R.id.action_bar_container),
                        withParent(allOf(withId(androidx.navigation.ui.R.id.decor_content_parent),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class)))),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(withId(androidx.navigation.ui.R.id.action_bar),
                        withParent(allOf(withId(androidx.navigation.ui.R.id.action_bar_container),
                                withParent(withId(androidx.navigation.ui.R.id.decor_content_parent)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
