package com.spbstu.sneakerhunterkotlin

import android.app.Instrumentation
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import com.spbstu.sneakerhunterkotlin.server_list.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instruments {
    fun selectTabAtPosition(tabIndex: Int): ViewAction {
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

    fun withDrawable(@DrawableRes id: Int) = object : TypeSafeMatcher<View>() {
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

@RunWith(AndroidJUnit4::class)
class SystemTest1 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)




    @Test
    fun checkMaleHistoryWorking() {

        var text = ""

        onView(withId(R.id.imageButtonMale))
                .perform(click())

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

        onView(withId(R.id.tabs))
                .perform(Instruments().selectTabAtPosition(1)).toString()

        Thread.sleep(500)

        onView(withId(R.id.history_button))
                .perform(click())


        Thread.sleep(500)
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

}
@RunWith(AndroidJUnit4::class)
class SystemTest2 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkFemaleHistoryWorking() {

        var text = ""

        onView(withId(R.id.imageButtonFemale))
                .perform(click())

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

        onView(withId(R.id.tabs))
                .perform(Instruments().selectTabAtPosition(1)).toString()

        Thread.sleep(500)

        onView(withId(R.id.history_button))
                .perform(click())

        Thread.sleep(500)
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

}
@RunWith(AndroidJUnit4::class)
class SystemTest3 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkMaleFavoritesWorking() {

        var text = ""

        onView(withId(R.id.imageButtonMale))
                .perform(click())

        Thread.sleep(500)
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
                .perform(Instruments().selectTabAtPosition(1)).toString()
        Thread.sleep(500)

        onView(withId(R.id.favorites_button))
                .perform(click())

        Thread.sleep(500)
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
                .check(matches(Instruments().withDrawable(R.drawable.baseline_favorite_24_liked)))
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
}

@RunWith(AndroidJUnit4::class)
class SystemTest4 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkFemaleFavoritesWorking() {

        var text = ""

        onView(withId(R.id.imageButtonFemale))
                .perform(click())
        onView(withId(R.id.query_edit_text))
                .perform(typeText("sneaker"), closeSoftKeyboard(), pressKey(KeyEvent.KEYCODE_ENTER))

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
                                        val textView =
                                                thisView.findViewById(R.id.element_name) as TextView
                                        text = textView.text.toString()

                                        click().perform(uiController, view)
                                    }
                                }

                        )
                )

        onView(withId(R.id.add_to_favorites_button)).perform(click())

        onView(withId(R.id.tabs))
                .perform(Instruments().selectTabAtPosition(1)).toString()
        Thread.sleep(500)

        onView(withId(R.id.favorites_button))
                .perform(click())

        Thread.sleep(500)
        onView(withId(R.id.search_recycler))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                                object : BoundedMatcher<View, CardView>(CardView::class.java) {
                                    override fun describeTo(description: Description?) {
                                        description?.appendText("find child with some text")
                                    }

                                    override fun matchesSafely(item: CardView?): Boolean {
                                        val thisView = item as CardView
                                        val textView =
                                                thisView.findViewById(R.id.element_name) as TextView

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
                                        val textView =
                                                thisView.findViewById(R.id.element_name) as TextView

                                        return textView.text.toString() == text
                                    }
                                },
                                click()
                        )
                )

        Thread.sleep(500)
        onView(allOf(withId(R.id.add_to_favorites_button), isDisplayed()))
                .check(matches(Instruments().withDrawable(R.drawable.baseline_favorite_24_liked)))
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
}
@RunWith(AndroidJUnit4::class)
class SystemTest5 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    lateinit var sneaker: Sneaker
    private lateinit var retrofit: Retrofit
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    private fun getSneakerItemFromServer(sneaker_id: Int) {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)

        val sneakerCall: Call<Sneaker?>? = sneakersAPI.getSneakerById(sneaker_id)
        sneakerCall?.enqueue(object : Callback<Sneaker?> {
            override fun onResponse(
                    call: Call<Sneaker?>,
                    response: Response<Sneaker?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { sneaker = it }
                }
            }

            override fun onFailure(
                    call: Call<Sneaker?>,
                    t: Throwable
            ) {
                println("fail: $t")
            }
        })
    }

    @Test
    fun checkMaleSearchByNameAndPriceWorking() {
        getSneakerItemFromServer(141)

        onView(withId(R.id.imageButtonMale))
                .perform(click())

        Thread.sleep(500)
        onView(withId(R.id.query_edit_text))
                .perform(
                        typeText(sneaker.name?.take(9)),
                        closeSoftKeyboard(),
                        pressKey(KeyEvent.KEYCODE_ENTER)
                )

        onView(withId(R.id.filter_button))
                .perform(click())

        onView(withId(R.id.priceFromEditText))
                .perform(typeText((sneaker.doubleMoney - 1.0).toString()), closeSoftKeyboard())
        onView(withId(R.id.priceToEditText))
                .perform(typeText((sneaker.doubleMoney + 1.0).toString()), closeSoftKeyboard())
        onView(withId(R.id.confirm_button))
                .perform(click())

        Thread.sleep(500)
        onView(withId(R.id.search_recycler))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                                object : BoundedMatcher<View, CardView>(CardView::class.java) {
                                    override fun describeTo(description: Description?) {
                                        description?.appendText("find child with some text")
                                    }

                                    override fun matchesSafely(item: CardView?): Boolean {
                                        val thisView = item as CardView
                                        val textView =
                                                thisView.findViewById(R.id.element_name) as TextView
                                        val priceTextView =
                                                thisView.findViewById(R.id.element_price) as TextView

                                        return textView.text == sneaker.name && priceTextView.text == sneaker.money
                                    }
                                },
                                scrollTo()
                        )
                )

        Thread.sleep(500)
        onView(withId(R.id.search_recycler))
                .perform(
                        RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                                object : BoundedMatcher<View, CardView>(CardView::class.java) {
                                    override fun describeTo(description: Description?) {
                                        description?.appendText("find child with some text")
                                    }

                                    override fun matchesSafely(item: CardView?): Boolean {
                                        val thisView = item as CardView
                                        val textView =
                                                thisView.findViewById(R.id.element_name) as TextView
                                        val priceTextView =
                                                thisView.findViewById(R.id.element_price) as TextView

                                        return textView.text == sneaker.name && priceTextView.text == sneaker.money
                                    }
                                },
                                click()
                        )
                )

        Thread.sleep(500)

        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(sneaker.uri))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        onView(withId(R.id.scroll_view_sneaker))
                .perform(swipeUp())
        onView(withId(R.id.sneaker_snopurl_button))
                .perform(click())
        intended(expectedIntent)
    }

}

@RunWith(AndroidJUnit4::class)
class SystemTest6 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    private lateinit var sneaker: Sneaker
    private lateinit var retrofit: Retrofit
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    private fun getSneakerItemFromServer(sneaker_id: Int) {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)

        val sneakerCall: Call<Sneaker?>? = sneakersAPI.getSneakerById(sneaker_id)
        sneakerCall?.enqueue(object : Callback<Sneaker?> {
            override fun onResponse(
                    call: Call<Sneaker?>,
                    response: Response<Sneaker?>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { sneaker = it }
                }
            }

            override fun onFailure(
                    call: Call<Sneaker?>,
                    t: Throwable
            ) {
                println("fail: $t")
            }
        })
    }

    @Test
    fun checkFemaleSearchByNameAndPriceWorking() {

        Thread.sleep(3000)

        onView(withId(R.id.imageButtonFemale))
                .perform(click())

        Thread.sleep(500)
        getSneakerItemFromServer(73)
        Thread.sleep(1500)
        onView(withId(R.id.query_edit_text))
                .perform(
                        typeText(sneaker.name?.take(9)),
                        closeSoftKeyboard(),
                        pressKey(KeyEvent.KEYCODE_ENTER)
                )

        onView(withId(R.id.filter_button))
                .perform(click())

        onView(withId(R.id.priceFromEditText))
                .perform(typeText((sneaker.doubleMoney - 1.0).toString()), closeSoftKeyboard())
        onView(withId(R.id.priceToEditText))
                .perform(typeText((sneaker.doubleMoney + 1.0).toString()), closeSoftKeyboard())
        onView(withId(R.id.confirm_button))
                .perform(click())

        Thread.sleep(500)
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
                                        val priceTextView =
                                                thisView.findViewById(R.id.element_price) as TextView

                                        return textView.text == sneaker.name && priceTextView.text == sneaker.money
                                    }
                                },
                                scrollTo()
                        )
                )

        Thread.sleep(500)
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
                                        val priceTextView =
                                                thisView.findViewById(R.id.element_price) as TextView

                                        return textView.text == sneaker.name && priceTextView.text == sneaker.money
                                    }
                                },
                                click()
                        )
                )

        val expectedIntent = allOf(hasAction(Intent.ACTION_VIEW), hasData(sneaker.uri))
        intending(expectedIntent).respondWith(Instrumentation.ActivityResult(0, null))
        onView(withId(R.id.scroll_view_sneaker))
                .perform(swipeUp())
        onView(withId(R.id.sneaker_snopurl_button))
                .perform(click())
        intended(expectedIntent)
    }
}


@RunWith(AndroidJUnit4::class)
class SystemTest7 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var elements: MutableList<Sneaker>
    private lateinit var retrofit: Retrofit
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    private fun parseJSONsFromServer() {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sneakers: Call<List<Sneaker>> = sneakersAPI.sneakers

        sneakers.enqueue(object : Callback<List<Sneaker>> {
            override fun onResponse(
                call: Call<List<Sneaker>>,
                response: Response<List<Sneaker>>
            ) {
                if (response.isSuccessful) {
                    elements = response.body() as MutableList<Sneaker>
                }
            }

            override fun onFailure(call: Call<List<Sneaker>>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    private fun getListOfElementsWithSize(size: Size): List<Sneaker> {
        return elements.filter { it.size!!.contains(size) }
    }

    @Test
    fun checkMaleSearchBySizeWorking() {

        parseJSONsFromServer()
        Thread.sleep(1000)

        val size = Size()
        size.id = 145
        size.size = "US 8.5"
        val listOfElements = getListOfElementsWithSize(size)

        onView(withId(R.id.imageButtonMale))
            .perform(click())

        Thread.sleep(1500)
        onView(withId(R.id.filter_button))
            .perform(click())

        onView(withId(R.id.sizeSpinner))
            .perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)), `is`(size.size)))
            .perform(click())
        onView(withId(R.id.sizeSpinner))
            .check(matches(withSpinnerText(containsString(size.size))))

        onView(withId(R.id.confirm_button))
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

                        for (temp in listOfElements) {
                            if (textView.text.toString() == temp.name)
                                return true
                        }
                        return false
                    }
                }
            )))
    }
}

@RunWith(AndroidJUnit4::class)
class SystemTest8 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    private lateinit var elements: MutableList<Sneaker>
    private lateinit var retrofit: Retrofit
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    private fun parseJSONsFromServer() {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sneakers: Call<List<Sneaker>> = sneakersAPI.sneakers

        sneakers.enqueue(object : Callback<List<Sneaker>> {
            override fun onResponse(
                    call: Call<List<Sneaker>>,
                    response: Response<List<Sneaker>>
            ) {
                if (response.isSuccessful) {
                    elements = response.body() as MutableList<Sneaker>
                }
            }

            override fun onFailure(call: Call<List<Sneaker>>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    private fun getListOfElementsWithSize(shopTitle: String, priceCase: Double): List<Sneaker> {
        return elements.filter { it.shop?.title.equals(shopTitle) }
                .filter { it.doubleMoney < priceCase }
    }

    @Test
    fun checkMaleSearchByFixedShopAndPriceWorking() {

        parseJSONsFromServer()
        Thread.sleep(1000)

        val shopTitle = "Ali Express"
        val priceUpperCase = 30.0
        val listOfElements = getListOfElementsWithSize(shopTitle, priceUpperCase)

        onView(withId(R.id.imageButtonMale))
                .perform(click())

        Thread.sleep(1500)
        onView(withId(R.id.filter_button))
                .perform(click())

        onView(withId(R.id.brandSpinner))
                .perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)), `is`(shopTitle)))
                .perform(click())
        Thread.sleep(500)
        onView(withId(R.id.brandSpinner))
                .check(matches(withSpinnerText(containsString(shopTitle))))

        onView(withId(R.id.priceToEditText))
                .perform(typeText(priceUpperCase.toString()), closeSoftKeyboard())

        onView(withId(R.id.confirm_button))
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

                                for (temp in listOfElements) {
                                    if (textView.text.toString() == temp.name)
                                        return true
                                }
                                return false
                            }
                        }
                )))
    }
}

@RunWith(AndroidJUnit4::class)
class SystemTest9 {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    private lateinit var elements: MutableList<Sneaker>
    private lateinit var retrofit: Retrofit
    private val client: Retrofit
        get() {
            retrofit = Retrofit.Builder()
                    .baseUrl(SneakersAPI.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit
        }

    private fun parseJSONsFromServer() {
        val sneakersAPI: SneakersAPI = client.create(SneakersAPI::class.java)
        val sneakers: Call<List<Sneaker>> = sneakersAPI.sneakers

        sneakers.enqueue(object : Callback<List<Sneaker>> {
            override fun onResponse(
                    call: Call<List<Sneaker>>,
                    response: Response<List<Sneaker>>
            ) {
                if (response.isSuccessful) {
                    elements = response.body() as MutableList<Sneaker>
                }
            }

            override fun onFailure(call: Call<List<Sneaker>>, t: Throwable) {
                println("fail: $t")
            }
        })
    }

    private fun getListOfElementsWithSize(brand: String,
                                          shop: String,
                                          priceLowerCase: Double): List<Sneaker> {
        return elements.filter { it.brand?.name.equals(brand) }
                .filter { it.shop?.title.equals(shop) }
                .filter { it.doubleMoney >= priceLowerCase }
    }

    @Test
    fun checkMaleSearchByFixedShopAndPriceWorking() {

        parseJSONsFromServer()
        Thread.sleep(1000)

        val shopTitle = "Asos"
        val brandTitle = "Nike Running"
        val priceLowerCase = 10.0
        val listOfElements = getListOfElementsWithSize(brandTitle, shopTitle, priceLowerCase)

        onView(withId(R.id.imageButtonMale))
                .perform(click())

        Thread.sleep(500)
        onView(withId(R.id.filter_button))
                .perform(click())

        onView(withId(R.id.priceFromEditText))
                .perform(typeText(priceLowerCase.toString()), closeSoftKeyboard())

        onView(withId(R.id.brandSpinner))
                .perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(shopTitle)))
                .perform(click())
        Thread.sleep(500)
        onView(withId(R.id.brandSpinner))
                .check(matches(withSpinnerText(containsString(shopTitle))))

        onView(withId(R.id.brandsSpinner))
                .perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(brandTitle)))
                .perform(click())
        Thread.sleep(500)
        onView(withId(R.id.brandsSpinner))
                .check(matches(withSpinnerText(containsString(brandTitle))))

        onView(withId(R.id.confirm_button))
                .perform(click())

        Thread.sleep(1500)
        onView(withId(R.id.search_recycler))
                .check(matches(not(
                        object : BoundedMatcher<View, CardView>(CardView::class.java) {
                            override fun describeTo(description: Description?) {
                                description?.appendText("find child with some text")
                            }

                            override fun matchesSafely(item: CardView?): Boolean {
                                val thisView = item as CardView
                                val textView = thisView.findViewById(R.id.element_name) as TextView

                                for (temp in listOfElements) {
                                    if (textView.text.toString() == temp.name)
                                        return true
                                }
                                return false
                            }
                        }
                )))
    }
}