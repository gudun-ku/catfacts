package com.beloushkin.test.catfacts.catfact

import android.content.res.Resources
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.beloushkin.test.catfacts.AndroidTestViewModel
import com.beloushkin.test.catfacts.R
import com.beloushkin.test.catfacts.TestDependencyInjection
import junit.framework.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_main.view.*
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class CatFactActivityTest {
    private val viewModel =
        TestDependencyInjection.catFactViewModel as AndroidTestViewModel

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(CatFactActivity::class.java)

    private val resources: Resources
        get() = activityRule.activity.resources

    @Test
    fun initialState() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.btn_get_fact_label)))
        assertEquals(viewModel.observableState.value, initialState)
    }

    @Test
    fun getFactButtonClickedAction() {
        val initialState = CatFactState()
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.getFactButton)).perform(click())

        viewModel.testAction.assertValues(CatFactAction.GetFactButtonClicked)
    }

    @Test
    fun loadingState() {
        val initialState = CatFactState(loading = true)
        viewModel.testState.onNext(initialState)
        onView(withId(R.id.loadingIndicator)).check(matches(isDisplayed()))
        onView(withId(R.id.catFactView)).check(matches(withText(R.string.btn_get_fact_label)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(not(isEnabled())))
    }

    @Test
    fun factLoadedState() {
        val fact = "Event cats need to be tested"
        val initialState = CatFactState(catFact = fact)
        viewModel.testState.onNext(initialState)

        onView(withId(R.id.loadingIndicator)).check(matches(not(isDisplayed())))
        onView(withId(R.id.catFactView)).check(matches(withText(fact)))
        onView(withId(R.id.errorView)).check(matches(not(isDisplayed())))
        onView(withId(R.id.getFactButton)).check(matches(isEnabled()))
    }
}