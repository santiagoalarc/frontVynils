package com.example.frontvynils.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.frontvynils.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestGuardarTrack {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testGuardarTrack() {
        val textView = onView(
            allOf(
                withText("Artistas"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java))),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.albumFragment),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        Thread.sleep(500)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val floatingActionButton = onView(
            allOf(
                withId(R.id.floatingSaveTrackActionButton),
                withContentDescription("Botón para guardar Track"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_host_fragment),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val appCompatEditText = onView(
            allOf(
                withId(R.id.editTextTrackName),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.card.MaterialCardView")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(replaceText("Idilio"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.editTextDurationTrack),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.card.MaterialCardView")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("2:30"), closeSoftKeyboard())

        val appCompatImageView = onView(
            allOf(
                withId(R.id.saveTrack), withContentDescription("Guardar Album"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("com.google.android.material.card.MaterialCardView")),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val recyclerView2 = onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        Thread.sleep(500)
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(0, click()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
