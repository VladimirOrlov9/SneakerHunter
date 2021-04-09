package com.spbstu.sneakerhunterkotlin

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.tabs.TabLayout
import com.spbstu.sneakerhunterkotlin.fragments.SneakerItemFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread


@RunWith(AndroidJUnit4::class)
class SystemTests {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(
                isDisplayed(),
                isAssignableFrom(TabLayout::class.java)
            )

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    @Test
    fun checkMaleHistoryWorking() {

        var text = ""

        onView(withId(R.id.imageButtonMale))
            .perform(click())

        onView(withId(R.id.search_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    object : ViewAction {
                        override fun getConstraints(): Matcher<View> {
                            return isAssignableFrom(TextView::class.java)
                        }

                        override fun getDescription(): String {
                            return "getting text from a TextView"
                        }

                        override fun perform(uiController: UiController?, view: View?) {
                            val thisView = view as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView
                            text = textView.text.toString()

                            click().perform(uiController, view)
                        }
                    }

                )
            )

        onView(withId(R.id.tabs))
            .perform(selectTabAtPosition(1)).toString()

        Thread.sleep(500)

        onView(withId(R.id.history_button))
            .perform(click())


        onView(withId(R.id.history_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    scrollTo()
                )
            )

        onView(withId(R.id.history_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    click()
                )
            )
    }

    @Test
    fun checkFemaleHistoryWorking() {

        var text = ""

        onView(withId(R.id.imageButtonFemale))
            .perform(click())

        onView(withId(R.id.search_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    object : ViewAction {
                        override fun getConstraints(): Matcher<View> {
                            return isAssignableFrom(TextView::class.java)
                        }

                        override fun getDescription(): String {
                            return "getting text from a TextView"
                        }

                        override fun perform(uiController: UiController?, view: View?) {
                            val thisView = view as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView
                            text = textView.text.toString()

                            click().perform(uiController, view)
                        }
                    }

                )
            )

        onView(withId(R.id.tabs))
            .perform(selectTabAtPosition(1)).toString()

        Thread.sleep(500)

        onView(withId(R.id.history_button))
            .perform(click())

        onView(withId(R.id.history_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    scrollTo()
                )
            )

        onView(withId(R.id.history_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    click()
                )
            )
    }

    @Test
    fun checkFavoritesWorking() {

        var text = ""

        onView(withId(R.id.imageButtonMale))
            .perform(click())
        onView(withId(R.id.query_edit_text))
            .perform(typeText("nike"), closeSoftKeyboard(), pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(500)
        onView(withId(R.id.search_recycler))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    object : ViewAction {
                        override fun getConstraints(): Matcher<View> {
                            return isAssignableFrom(TextView::class.java)
                        }

                        override fun getDescription(): String {
                            return "getting text from a TextView"
                        }

                        override fun perform(uiController: UiController?, view: View?) {
                            val thisView = view as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView
                            text = textView.text.toString()

                            click().perform(uiController, view)
                        }
                    }

                )
            )

        onView(withId(R.id.add_to_favorites_button)).perform(click())

        onView(withId(R.id.tabs))
            .perform(selectTabAtPosition(1)).toString()
        Thread.sleep(500)

        onView(withId(R.id.favorites_button))
            .perform(click())

        onView(withId(R.id.search_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    scrollTo()
                )
            )

        onView(withId(R.id.search_recycler))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    object : BoundedMatcher<View, CardView>(CardView::class.java) {
                        override fun describeTo(description: Description?) {
                            description?.appendText("find child with some text")
                        }

                        override fun matchesSafely(item: CardView?): Boolean {
                            val thisView = item as CardView
                            val textView = thisView.findViewById(R.id.element_name) as TextView

                            return textView.text.toString() == text
                        }
                    },
                    click()
                )
            )

        Thread.sleep(500)
        onView(allOf(withId(R.id.add_to_favorites_button), isDisplayed()))
                .check(matches(withDrawable(R.drawable.baseline_favorite_24_liked)))
                .perform(click())

        Thread.sleep(500)
        onView(allOf(withContentDescription(R.string.abc_action_bar_up_description), isDisplayed()))
                .perform(click())


        Thread.sleep(500)
        onView(withId(R.id.search_recycler))
                .check(matches(not(
                        object : BoundedMatcher<View, CardView>(CardView::class.java) {
                            override fun describeTo(description: Description?) {
                                description?.appendText("find child with some text")
                            }

                            override fun matchesSafely(item: CardView?): Boolean {
                                val thisView = item as CardView
                                val textView = thisView.findViewById(R.id.element_name) as TextView

                                return textView.text.toString() == text
                            }
                        }
                )))
    }

    private fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val expectedBitmap = context.getDrawable(id)?.toBitmap()

            return view is ImageView && view.drawable.toBitmap().sameAs(expectedBitmap)
        }
    }
}